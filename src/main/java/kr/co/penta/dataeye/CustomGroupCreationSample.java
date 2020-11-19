package kr.co.penta.dataeye;

import com.microstrategy.web.objects.EnumWebCustomGroupDisplayOptions;
import com.microstrategy.web.objects.WebAttribute;
import com.microstrategy.web.objects.WebAttributeForm;
import com.microstrategy.web.objects.WebAttributeForms;
import com.microstrategy.web.objects.WebCustomGroup;
import com.microstrategy.web.objects.WebElement;
import com.microstrategy.web.objects.WebElements;
import com.microstrategy.web.objects.WebElementsObjectNode;
import com.microstrategy.web.objects.WebExpression;
import com.microstrategy.web.objects.WebFilter;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebMetric;
import com.microstrategy.web.objects.WebNode;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebOperatorNode;
import com.microstrategy.webapi.EnumDSSXMLAEAggregation;
import com.microstrategy.webapi.EnumDSSXMLDataType;
import com.microstrategy.webapi.EnumDSSXMLExpressionType;
import com.microstrategy.webapi.EnumDSSXMLFilterType;
import com.microstrategy.webapi.EnumDSSXMLFolderNames;
import com.microstrategy.webapi.EnumDSSXMLFunction;
import com.microstrategy.webapi.EnumDSSXMLNodeDimty;
import com.microstrategy.webapi.EnumDSSXMLObjectFlags;
import com.microstrategy.webapi.EnumDSSXMLObjectSubTypes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

public class CustomGroupCreationSample implements EnumDSSXMLDataType {
	//아직 파악중
	//Intelligence Server Session 인텔리전스 서버 세션
    private static WebIServerSession _serverSession = null;

    //Object Source used to obtain filters and folders 필터 및 폴더를 얻는 데 사용되는 객체 소스
    private static WebObjectSource _objSource = null;

    //Custom Group to be saved in this sample class 이 샘플 클래스에 저장 될 사용자 정의 그룹
    private static WebCustomGroup _customGroup = null;

    /**
     * Main method to execute all sample scenarios 모든 샘플 시나리오를 실행하는 기본 방법
     * @param args String[]
     */
    public static void main(String args[]) {
        try {
            //Create a session using the SessionManagementSample class( SessionManagementSample 클래스를 사용하여 세션 만들기)
            _serverSession = SessionManagementSample.getSession();

            //Create a Custom Group using a shortcut to a filter(필터 바로 가기를 사용하여 사용자 정의 그룹 생성)
            _customGroup = getNewCustomGroup();
            createShortcutToFilter(_customGroup, "87A6E4234A49CE203613829BDCDE1F66", "Top 5 Customers by Revenue");

            // Create a Custom Group using In List Attribute Qualification(In List Attribute Qualification을 사용하여 사용자 지정 그룹 생성)
            _customGroup = getNewCustomGroup();
            createInListAttributeQualification(_customGroup, "Northern Regions");

            // Create a Custom Group using "Begins With" in the Attribute Qualification(속성 검증에서 "Begins With"를 사용하여 사용자 정의 그룹 생성)
            _customGroup = getNewCustomGroup();
            createBeginsWithAttributeQualification(_customGroup, "Employee's last name begins with A");

            // Create a Custom Group using a Metric Qualification(메트릭 검증을 사용하여 사용자 정의 그룹 생성)
            _customGroup = getNewCustomGroup();
            createMetricQualification(_customGroup, "Revenue greater than 1000 at the region level");

            // Create a Custom Group Banding Qualification(사용자 지정 그룹 밴딩 자격 만들기)
            _customGroup = getNewCustomGroup();
            createCustomGroupBandingQualification(_customGroup, "Banding on Revenue at the Country Level");

            //Close session
            _serverSession.closeSession();

        } catch (WebObjectsException webObjEx) {
            System.out.println("Error in main: " + webObjEx.getMessage());
        }
    }

    /**
     *
     * UTILITY METHODS
     *
     */

    /**
     * Generates a new Custom Group instance(새로운 사용자 정의 그룹 인스턴스를 생성합니다)
     * @return WebCustomGroup
     */
    public static WebCustomGroup getNewCustomGroup() {
        //Obtain the Object Source Object to obtain other objects from it
    	//다른 오브젝트를 얻기 위해 오브젝트 소스 오브젝트를 확보하십시오.
        _objSource = _serverSession.getFactory().getObjectSource();

        //Create empty Custom Group object(빈 사용자 정의 그룹 객체 생성)
        return (WebCustomGroup) _objSource.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeFilter, EnumDSSXMLObjectSubTypes.DssXmlSubTypeCustomGroup);
    }

    /**
     * Saves a custom group to the "My Objects" folder.(사용자 정의 그룹을 "My Objects"폴더에 저장합니다.)
     * @param cg - The custom group to save(저장할 사용자 정의 그룹)
     * @param customGroupName - The name of the custom group to be saved
     */
    public static void saveCustomGroup(WebCustomGroup cg, String customGroupName) {
        try {
            //Get a reference to the "My Objects" folder
            WebFolder saveToFolder = (WebFolder) _objSource.getObject(_objSource.getFolderID(EnumDSSXMLFolderNames.DssXmlFolderNameProfileOther), EnumDSSXMLObjectTypes.DssXmlTypeFolder);

            //Set save as flags
            int originalFlags = _objSource.getFlags();

            _objSource.setFlags(originalFlags | EnumDSSXMLObjectFlags.DssXmlObjectSaveOverwrite);

            //Save the custom group to the "My Objects" folder. Overwrite if necessary.
            _objSource.save(cg, customGroupName, saveToFolder);
        } catch (WebObjectsException ex) {
            SessionManagementSample.handleError(_serverSession, "Error obtaining target folder: " + ex.getMessage());
        }
    }

    /**
     *
     * CUSTOM GROUP CREATION
     *
     */


    /**
     * Creates a Custom Group using an existing filter definition. 기존 필터 정의를 사용하여 사용자 지정 그룹을 만듭니다.
     * @param cg WebCustomGroup - A new custom group instance 새로운 커스텀 그룹 인스턴스
     * @param filterID String - The ID of the filter to be used in the CG definition CG 정의에 사용될 필터의 ID
     * @param String expressionName - The name used when saving the Custom Group 사용자 정의 그룹을 저장할 때 사용되는 이름
     * @return WebCustomGroup - The new Custom Group 새로운 사용자 정의 그룹
     */
    public static WebCustomGroup createShortcutToFilter(WebCustomGroup cg, String filterID, String expressionName) {
        try {

            //Get the empty expression from the object
            WebExpression expr = cg.getExpression();

            // Set the root node function to OR always when creating a Custom Group
            WebOperatorNode root = (WebOperatorNode) expr.getRootNode();

            root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);

            //Attach a Filter Embeded Qualification to the root node using a shortcut to an existing filter
            WebFilter filterObj = (WebFilter) _objSource.getObject(filterID, EnumDSSXMLObjectTypes.DssXmlTypeFilter);
            WebNode srcNode = expr.createShortcutNode(filterObj, root);

            srcNode.setExpressionType(EnumDSSXMLExpressionType.DssXmlFilterEmbedQual);

            //Set the display name for the custom group
            srcNode.setDisplayName(expressionName);

            /**
             * Save Misc Custom Group Properties
             */
            //Allow aggregation on the CG
            //Enabled - EnumDSSXMLAEAggregation.DssXmlAggregationEnable
            //Disabled - EnumDSSXMLAEAggregation.DssXmlAggregationDisable
            cg.setAggregation(EnumDSSXMLAEAggregation.DssXmlAggregationDisable);

            //Enable Hierarchical Display
            cg.setFlatten(true);

            //Custom Group Element Header Display Option
            //Above Child Elements - true
            //Below Child Elements - false
            cg.setParentFirst(true);

            // Set Display Options
            // See EnumWebCustomGroupDisplayOptions for all the available Display Options
            cg.setNodeKey(srcNode.getKey());
            cg.setDisplayOption(EnumWebCustomGroupDisplayOptions.ShowOnlyElementNames);

            //Save Custom Group
            saveCustomGroup(cg, expressionName);

        } catch (WebObjectsException ex) {
            SessionManagementSample.handleError(_serverSession, "Error fetching filter: " + ex.getMessage());
        }
        return cg;
    }

    /**
     * Creates a Banding Qualification Custom Group
     * @param cg WebCustomGroup - A new custom group instance
     * @param expressionName String - The name used when saving the Custom Group. Also used as the name of the expression.
     * @return WebCustomGroup - The new Custom Group
     */
    public static WebCustomGroup createCustomGroupBandingQualification(WebCustomGroup cg, String expressionName) {
        try {

            WebExpression expression = cg.getExpression();

            // Set the root node function to OR always when creating a Custom Group
            WebOperatorNode root = (WebOperatorNode) expression.getRootNode();

            root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);

            //Attach a Banding Expression to the root and set the displayName for the Custom Group on it
            WebOperatorNode opNode = expression.createOperatorNode(EnumDSSXMLExpressionType.DssXmlExpressionBanding, EnumDSSXMLFunction.DssXmlFunctionBanding, root);

            opNode.setDisplayName(expressionName);

            /**
             * Attach the metric, startAt, StopAt and Step Size nodes to the expression(메트릭, startAt, StopAt 및 Step Size 노드를 표현식에 연결하십시오.)
             */
            //Atach a shortcut node to the "Revenue" metric('수익'측정 항목에 대한 바로 가기 노드에 도달)
            // 4C05177011D3E877C000B3B2D86C964F - Revenue
            WebMetric profit = (WebMetric) _objSource.getObject("4C05177011D3E877C000B3B2D86C964F", EnumDSSXMLObjectTypes.DssXmlTypeMetric);

            expression.createShortcutNode(profit, opNode);
            // Insert the banding StartAt value
            expression.createConstantNode("1", EnumDSSXMLDataType.DssXmlDataTypeReal, opNode);
            // Insert the banding StopAt value
            expression.createConstantNode("1000", EnumDSSXMLDataType.DssXmlDataTypeReal, opNode);
            // Insert the banding StepSize value
            expression.createConstantNode("100", EnumDSSXMLDataType.DssXmlDataTypeReal, opNode);

            // Set the dimensionality at the "Country" level
            //8D679D3811D3E4981000E787EC6DE8A4 - Country
            WebObjectInfo countryAtt = _objSource.getObject("8D679D3811D3E4981000E787EC6DE8A4", EnumDSSXMLObjectTypes.DssXmlTypeAttribute);
            WebAttribute att = (WebAttribute) countryAtt;

            opNode.getDimensionality().add(att);
            opNode.setDimensionalityType(EnumDSSXMLNodeDimty.DssXmlNodeDimtyOutputLevel);

            /**
             * Save Misc Custom Group Properties
             */
            //Disable aggregation on the CG
            //Enabled - EnumDSSXMLAEAggregation.DssXmlAggregationEnable
            //Disabled - EnumDSSXMLAEAggregation.DssXmlAggregationDisable
            cg.setAggregation(EnumDSSXMLAEAggregation.DssXmlAggregationDisable);

            //Disable Hierarchical Display
            //Enable Hierarchical Display - False
            //Disable Hierarchical Display - true
            cg.setFlatten(true);

            //Custom Group Element Header Display Option
            //Above Child Elements - true
            //Below Child Elements - false
            cg.setParentFirst(false);

            //Set Display Option
            cg.setNodeKey(opNode.getKey());
            cg.setDisplayOption(EnumWebCustomGroupDisplayOptions.ShowOnlyElementNames);

            saveCustomGroup(cg, expressionName);

        } catch (WebObjectsException woEx) {
            SessionManagementSample.handleError(_serverSession, "Error: " + woEx.getMessage());
        } finally {
            return cg;
        }
    }

    /**
     * Creates an In List Attribute Qualification Custom Group
     * @param cg WebCustomGroup - A new custom group instance
     * @param expressionName String - The name used when saving the Custom Group. Also used as the name of the expression.
     * @return WebCustomGroup - The new Custom Group
     */
    public static WebCustomGroup createInListAttributeQualification(WebCustomGroup cg, String expressionName) {
        try {

            // Set the filter type to make it truly a custom group
            cg.setFilterType(EnumDSSXMLFilterType.DssXmlCustomGroup);

            // Get the expression from the custom group
            WebExpression expression = cg.getExpression();

            //Create the OR operator because by default the root is an AND operator
            WebOperatorNode root = (WebOperatorNode) expression.getRootNode();

            root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);

            //Create In operator node and attach it to the expression
            WebOperatorNode opNode = expression.createOperatorNode(EnumDSSXMLExpressionType.DssXmlFilterListQual, EnumDSSXMLFunction.DssXmlFunctionIn, root);

            //Display name for CG
            opNode.setDisplayName(expressionName);

            //Attach a shortcut to the "Region" attribute
            //8D679D4B11D3E4981000E787EC6DE8A4 - Region
            WebAttribute regionAtt = (WebAttribute) _objSource.getObject("8D679D4B11D3E4981000E787EC6DE8A4", EnumDSSXMLObjectTypes.DssXmlTypeAttribute);

            expression.createShortcutNode(regionAtt, opNode);

            //Create the Element List Qualification, then append elements to it.
            WebElementsObjectNode elemsNode = expression.createElementsObjectNode(regionAtt, opNode);

            //Get attribute elements from the Intelligence Server
            regionAtt.populate();

            //Add Northern regions to the attribute qualification
            WebElements elements = regionAtt.getElementSource().getElements();
            WebElement element = null;

            for (int i = 0; i < elements.size(); i++) {
                element = elements.get(i);
                if (element.getDisplayName().startsWith("North")) {
                    elemsNode.getElements().add(element.getElementID(), element.getDisplayName());
                }
            }

            /**
             * Save Misc Custom Group Properties
             */
            //Allow aggregation on the CG
            //Enabled - EnumDSSXMLAEAggregation.DssXmlAggregationEnable
            //Disabled - EnumDSSXMLAEAggregation.DssXmlAggregationDisable
            cg.setAggregation(EnumDSSXMLAEAggregation.DssXmlAggregationEnable);

            //Disable Hierarchical Display
            cg.setFlatten(true);

            //Custom Group Element Header Display Option
            //Above Child Elements - true
            //Below Child Elements - false
            cg.setParentFirst(true);

            // Show only the individual items within this element
            cg.setNodeKey(opNode.getKey());
            cg.setDisplayOption(EnumWebCustomGroupDisplayOptions.ShowOnlyIndividualItems);

            saveCustomGroup(cg, expressionName);

        } catch (WebObjectsException woEx) {
            SessionManagementSample.handleError(_serverSession, "Error: " + woEx.getMessage());
        } finally {
            return cg;
        }
    }

    /**
     * Creates an Attribute Qualification Custom Group (using "Begins with" in the expression)
     * @param cg WebCustomGroup - A new custom group instance
     * @param expressionName String - The name used when saving the Custom Group. Also used as the name of the expression.
     * @return WebCustomGroup - The new Custom Group
     */
    public static WebCustomGroup createBeginsWithAttributeQualification(WebCustomGroup cg, String expressionName) {
        try {
            // Set the filter type to make it truly a custom group
            cg.setFilterType(EnumDSSXMLFilterType.DssXmlCustomGroup);

            // Get the expression from the custom group
            WebExpression expression = cg.getExpression();

            // Set the root node function to Begins with and expression type to DssXmlFilterSingleBaseFormQual
            WebOperatorNode root = (WebOperatorNode) expression.getRootNode();

            root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);

            // Add a shortcut node to an attribute: employee
            // Employee - 8D679D3F11D3E4981000E787EC6DE8A4
            WebAttribute employee = (WebAttribute) _objSource.getObject("8D679D3F11D3E4981000E787EC6DE8A4", EnumDSSXMLObjectTypes.DssXmlTypeAttribute, true);

            //Get attribute elements from the Intelligence Server
            WebAttributeForms empForms = employee.getBrowseForms();
            WebAttributeForm lastName = empForms.get(0);

            WebOperatorNode opNode = expression.createOperatorNode(EnumDSSXMLExpressionType.DssXmlFilterSingleBaseFormQual, EnumDSSXMLFunction.DssXmlFunctionBeginsWith, root);

            opNode.setDisplayName(expressionName);

            //Set the "Last Name" attribute form in the expression
            expression.createFormShortcutNode(employee, lastName, opNode);

            // Add a constant node for the attribute qualification to build the expression "Employee (Last Name) begins with A"
            expression.createConstantNode("A", lastName.getBaseAttributeFormDataType(), opNode);

            /**
             * Save Misc Custom Group Properties
             */
            //Disable aggregation on the CG
            //Enabled - EnumDSSXMLAEAggregation.DssXmlAggregationEnable
            //Disabled - EnumDSSXMLAEAggregation.DssXmlAggregationDisable
            cg.setAggregation(EnumDSSXMLAEAggregation.DssXmlAggregationDisable);

            //Disable Hierarchical Display
            cg.setFlatten(false);

            //Custom Group Element Header Display Option
            //Above Child Elements - true
            //Below Child Elements - false
            cg.setParentFirst(false);

            saveCustomGroup(cg, expressionName);

        } catch (WebObjectsException woEx) {
            SessionManagementSample.handleError(_serverSession, "Error: " + woEx.getMessage());
        } finally {
            return cg;
        }
    }

    /**
     * Creates a new Metric Qualification Custom Group
     * @param cg WebCustomGroup - A new custom group instance
     * @param expressionName String - The name used when saving the Custom Group. Also used as the name of the expression.
     * @return WebCustomGroup - The new Custom Group
     */
    public static WebCustomGroup createMetricQualification(WebCustomGroup cg, String expressionName) {
        try {
            // Set the filter type to make it truly a custom group
            cg.setFilterType(EnumDSSXMLFilterType.DssXmlCustomGroup);

            // Get the expression from the custom group
            WebExpression expression = cg.getExpression();

            // Set the root node function to Greater and expression type to FilterSingleMetricQual
            WebOperatorNode root = (WebOperatorNode) expression.getRootNode();

            root.setFunction(EnumDSSXMLFunction.DssXmlFunctionOr);

            //Attach a Banding Expression to the root and set the displayName for the Custom Group on it
            WebOperatorNode opNode = expression.createOperatorNode(EnumDSSXMLExpressionType.DssXmlFilterSingleMetricQual, EnumDSSXMLFunction.DssXmlFunctionGreater, root);

            opNode.setDisplayName(expressionName);

            // Add a shortcut node to a metric: "revenue"
            //Revenue - 4C05177011D3E877C000B3B2D86C964F
            WebMetric metricObj = (WebMetric) _objSource.getObject("4C05177011D3E877C000B3B2D86C964F", EnumDSSXMLObjectTypes.DssXmlTypeMetric);

            expression.createShortcutNode(metricObj, opNode);

            // Add a constant node for the metric qualification
            expression.createConstantNode("1000", EnumDSSXMLDataType.DssXmlDataTypeReal, opNode);

            // Set the dimensionality on the metric qualification at the "Region" level
            // Region - 8D679D4B11D3E4981000E787EC6DE8A4
            WebObjectInfo regionAtt = _objSource.getObject("8D679D4B11D3E4981000E787EC6DE8A4", EnumDSSXMLObjectTypes.DssXmlTypeAttribute);

            opNode.getDimensionality().add(regionAtt);
            opNode.setDimensionalityType(EnumDSSXMLNodeDimty.DssXmlNodeDimtyOutputLevel);

            /**
             * Save Misc Custom Group Properties
             */
            //Allow aggregation on the CG
            //Enabled - EnumDSSXMLAEAggregation.DssXmlAggregationEnable
            //Disabled - EnumDSSXMLAEAggregation.DssXmlAggregationDisable
            cg.setAggregation(EnumDSSXMLAEAggregation.DssXmlAggregationEnable);

            //Enable Hierarchical Display
            cg.setFlatten(true);

            //Custom Group Element Header Display Option
            //Above Child Elements - true
            //Below Child Elements - false
            cg.setParentFirst(false);

            saveCustomGroup(cg, expressionName);

        } catch (WebObjectsException woEx) {
            SessionManagementSample.handleError(_serverSession, "Error: " + woEx.getMessage());
        } finally {
            return cg;
        }
    }

}
