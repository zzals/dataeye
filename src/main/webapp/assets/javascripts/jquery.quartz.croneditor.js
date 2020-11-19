;(function ($) {
	$.uuid = function() {
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
			var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
			return v.toString(16);
		});
	};
	
	var $el;
	var settings = {"value":"", "scheduledUrl":"", "resultCnt":5, "sampleDisplay":true};
	var methods = {
		init : function(options) { 
			return this.each(function(index, value) {
				var uuid = $.uuid();
				var huuid = $.uuid();
				var muuid = $.uuid();
				var yuuid = $.uuid();
				// HTML Template for plugin
				var tmpl = '<div id="'+uuid+'">';
				tmpl += '	<div id="tabs" class="tabs" role="quartz-schedule">';
				tmpl += '		<ul>';
				tmpl += '			<li><a href="#tabs-minutes">Minutes</a></li>';
				tmpl += '			<li><a href="#tabs-hourly">Hourly</a></li>';
				tmpl += '			<li><a href="#tabs-dayily">Daily</a></li>';
				tmpl += '			<li><a href="#tabs-weekly">Weekly</a></li>';
				tmpl += '			<li><a href="#tabs-monthly">Monthly</a></li>';
				tmpl += '			<li><a href="#tabs-yearly">Yearly</a></li>';
				tmpl += '		</ul>';
				tmpl += '		<div id="tabs-minutes">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Every</label>';
				tmpl += '					<select class="form-control" id="minutenumctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">minute(s)</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '		<div id="tabs-hourly">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+huuid+'" id="hourlyRadio" value="0" checked="">Every';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="hournumctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">hour(s)</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+huuid+'" id="hourlyRadio" value="1">Starts at';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="hourctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="minutectrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '		<div id="tabs-dayily">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Every</label>';
				tmpl += '					<select class="form-control" id="dayctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">day(s)</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Starts time</label>';
				tmpl += '					<select class="form-control" id="hourctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="minutectrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '		<div id="tabs-weekly">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="MON">Monday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="TUE">Tuesday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="WED">Wednesday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="THU">Thursday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="FRI">Friday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="SAT">Saturday</label>';
				tmpl += '					<label class="checkbox-inline"><input type="checkbox" id="weeklyCheck" value="SUN">Sunday</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Starts time</label>';
				tmpl += '					<select class="form-control" id="hourctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="minutectrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '		<div id="tabs-monthly">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+muuid+'" id="monthlyRadio" value="0" checked="">Day';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="dayctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">of every</label>';
				tmpl += '					<select class="form-control" id="monthctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">month(s)</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+muuid+'" id="monthlyRadio" value="1">The';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="weekthctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="dayofweekctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">of every</label>';
				tmpl += '					<select class="form-control" id="monthctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">month(s)</label>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Starts time</label>';
				tmpl += '					<select class="form-control" id="hourctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="minutectrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '		<div id="tabs-yearly">';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+yuuid+'" id="yearlyRadio" value="0" checked="">Every';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="monthofyearctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="dayctrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">';
				tmpl += '						<input type="radio" name="'+yuuid+'" id="yearlyRadio" value="1">The';
				tmpl += '					</label>';
				tmpl += '					<select class="form-control" id="weekthctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="dayofweekctrl">';
				tmpl += '					</select>';
				tmpl += '					<label class="control-label">of</label>';
				tmpl += '					<select class="form-control" id="monthofyearctrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '			<div class="form-inline" role="form">';
				tmpl += '				<div class="form-group">';
				tmpl += '					<label class="control-label">Starts time</label>';
				tmpl += '					<select class="form-control" id="hourctrl">';
				tmpl += '					</select>';
				tmpl += '					<select class="form-control" id="minutectrl">';
				tmpl += '					</select>';
				tmpl += '				</div>';
				tmpl += '			</div>';
				tmpl += '		</div>';			
				tmpl += '	</div>';
				tmpl += '	<div style="margin:15px;">';
				tmpl += '		<div class="form-inline" role="form">';
				tmpl += '			<div class="form-group">';
				tmpl += '				<button type="button" id="btnCronExpression" class="btn btn-warning">Generate Cron Expression</button>';
				tmpl += '				<label class="sr-only" for="cronExpression">Cron Expression</label>';
				tmpl += '				<input type="text" class="form-control" id="cronExpression" placeholder="Cron Expression">';
				tmpl += '			</div>';
				tmpl += '		</div>';
				tmpl += '	</div>';
				tmpl += '	<div id="sampleDisplay" style="margin:15px;">';
				tmpl += '		<div class="form-inline" role="form" style="border: 1px;border-style: solid;padding: 5px 20px;height: 190px;overflow: auto;">';
				tmpl += '			<div class="form-group">';
				tmpl += '				<label class="control-label">Next Scheduled : </label>';
				tmpl += '				<select class="form-control" id="sampleCnt">';
				tmpl += '					<option value="5">5</option>';
				tmpl += '					<option value="10">10</option>';
				tmpl += '				</select>'; 
				tmpl += '				<div id="samples">';
				tmpl += '				</div>';
				tmpl += '			</div>'; 
				tmpl += '		</div>';
				tmpl += '	</div>';
				tmpl += '</div>';
				
				var minutearr = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09"
							   , "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
							   , "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"
							   , "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"
							   , "40", "41", "42", "43", "44", "45", "46", "47", "48", "49"
							   , "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"];

				var hourarr = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"
							 , "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];

				var dayarr = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
							, "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
							, "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
							, "31"];

				var montharr = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
				
				var monthofyeararr = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
				
				var dayofweekarr = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
				var dayofweekvalarr = ["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"];
				
				var weektharr = ["First", "Second", "Third", "Fourth"];
				
				$el = $(this); 
				$el.html(tmpl);
				if (!options.sampleDisplay) {
					$("#sampleDisplay", $el).css("display", "none");
				}
				$.each(minutearr, function(index, value){
					$("select#minutectrl", $el).append("<option value='"+value+"'>"+value+"</option>");
				});
				$.each(minutearr, function(index, value){
					if (index != 0) {
						$("select#minutenumctrl", $el).append("<option value='"+value+"'>"+value+"</option>");
					}
				});
				$.each(hourarr, function(index, value){
					$("select#hourctrl", $el).append("<option value='"+value+"'>"+value+"</option>");
				});
				$.each(hourarr, function(index, value){
					if (index != 0) {
						$("select#hournumctrl", $el).append("<option value='"+value+"'>"+value+"</option>");
					}
				});
				$.each(dayarr, function(index, value){
					$("select#dayctrl", $el).append("<option value='"+value+"'>"+value+"</option>");
				});
				$.each(montharr, function(index, value){
					$("select#monthctrl", $el).append("<option value='"+value+"'>"+value+"</option>");
				});
				$.each(monthofyeararr, function(index, value){
					$("select#monthofyearctrl", $el).append("<option value='"+(index+1)+"'>"+value+"</option>");
				});
				$.each(dayofweekarr, function(index, value){
					$("select#dayofweekctrl", $el).append("<option value='"+dayofweekvalarr[index]+"'>"+value+"</option>");
				});
				$.each(weektharr, function(index, value){
					$("select#weekthctrl", $el).append("<option value='"+(index+1)+"'>"+value+"</option>");
				});

				$( ".tabs", $el).tabs({
					activate: function( event, ui ) {
						$("#cronExpression", $el).val("");
						$("#samples", $el).empty();
					}
				});

				$( "#btnCronExpression", $el).on( "click", function(e){
					var $tabs = $( "#tabs", $el );
					var idx = $tabs.tabs("option", "active");
					var $div = $("> div:eq("+idx+")", $tabs );
					if (idx == 0) {
						var v = $("#minutenumctrl", $div).val();
						var exp = "0 0/"+parseInt(v)+" * 1/1 * ? *"
						$("#cronExpression", $el).val(exp);
					} else if (idx == 1) {
						if ($("#hourlyRadio:checked", $div).val() == 0) {
							var v = $("#hournumctrl", $div).val();
							var exp = "0 0 0/"+parseInt(v)+" 1/1 * ? *";
							$("#cronExpression", $el).val(exp);
						} else if ($("#hourlyRadio:checked", $div).val() == 1) {
							var v1 = $("#hourctrl", $div).val();
							var v2 = $("#minutectrl", $div).val();
							var exp = "0 "+parseInt(v2)+" "+parseInt(v1)+" 1/1 * ? *";
							$("#cronExpression", $el).val(exp);
						}
					} else if (idx == 2) {
						var v1 = $("#dayctrl", $div).val();
						var v2 = $("#hourctrl", $div).val();
						var v3 = $("#minutectrl", $div).val();
						var exp = "0 "+parseInt(v3)+" "+parseInt(v2)+" 1/"+parseInt(v1)+" * ? *";
						$("#cronExpression", $el).val(exp);
					} else if (idx == 3) {
						var chks = $("#weeklyCheck:checked", $div);
						if (chks.length ==0) {
							alert("'Days selection' 필드는 필수 입력 사항입니다.");
							return;
						}
						var chksVal = [];
						$.each(chks, function(index, value){
							chksVal.push($(chks[index]).val());
						});
						var v1 = chksVal.join(",");
						var v2 = $("#hourctrl", $div).val();
						var v3 = $("#minutectrl", $div).val();
						var exp = "0 "+parseInt(v3)+" "+parseInt(v2)+" ? * "+v1+" *";
						$("#cronExpression", $el).val(exp);
					} else if (idx == 4) {
						if ($("#monthlyRadio:checked", $div).val() == 0) {
							var v1 = $("#dayctrl", $div).val();
							var v2 = $("select#monthctrl:eq(0)", $div).val();
							var v3 = $("#hourctrl", $div).val();
							var v4 = $("#minutectrl", $div).val();
							var exp = "0 "+parseInt(v4)+" "+parseInt(v3)+" "+parseInt(v1)+" 1/"+parseInt(v2)+" ? *";
							$("#cronExpression", $el).val(exp);
						} else if ($("#monthlyRadio:checked", $div).val() == 1) {
							var v1 = $("#weekthctrl", $div).val();
							var v2 = $("#dayofweekctrl", $div).val();
							var v3 = $("select#monthctrl:eq(1)", $div).val();
							var v4 = $("#hourctrl", $div).val();
							var v5 = $("#minutectrl", $div).val();
							var exp = "0 "+parseInt(v5)+" "+parseInt(v4)+" ? 1/"+parseInt(v3)+" "+v2+"#"+parseInt(v1)+" *";
							$("#cronExpression", $el).val(exp);
						}
					} else if (idx == 5) {
						if ($("#yearlyRadio:checked", $div).val() == 0) {
							var v1 = $("select#monthofyearctrl:eq(0)", $div).val();
							var v2 = $("#dayctrl", $div).val();
							var v3 = $("#hourctrl", $div).val();
							var v4 = $("#minutectrl", $div).val();
							var exp = "0 "+parseInt(v4)+" "+parseInt(v3)+" "+parseInt(v2)+" "+parseInt(v1)+" ? *";
							$("#cronExpression", $el).val(exp);
						} else if ($("#yearlyRadio:checked", $div).val() == 1) {
							var v1 = $("#weekthctrl", $div).val();
							var v2 = $("#dayofweekctrl", $div).val();
							var v3 = $("select#monthofyearctrl:eq(1)", $div).val();
							var v4 = $("#hourctrl", $div).val();
							var v5 = $("#minutectrl", $div).val();
							var exp = "0 "+parseInt(v5)+" "+parseInt(v4)+" ? "+parseInt(v3)+" "+v2+"#"+parseInt(v1)+" *";
							$("#cronExpression", $el).val(exp);
						}
					}
					if (settings["sampleDisplay"]) {
						$("#samples", $el).empty(); 
						var samples = methods.getScheduledSample();
						$.each(samples, function(index, value){
							$("#samples", $el).append("<h5><strong>"+(index+1)+". </strong>"+value+"</h5>");
						});
					}
				});
			});
		},
		getSchedule : function( ) { 
			return $("#cronExpression", $el).val();
		},
		reset : function() {
			$( ".tabs", $el).tabs( "option", "active", 0);
			$("#cronExpression", $el).val("");
			$("#samples", $el).empty();
		},
		getScheduledSample : function( ) {
			if ($("#cronExpression", $el).val() == "") {
				return [];
			}
			if (settings.scheduledUrl) {
				var ret = [];
				var postData = {
					"cronExpression":$("#cronExpression", $el).val(),
					"resultCount":$("#sampleCnt", $el).val()
				};
				$.ajax({
					type: "POST",
					url: settings.scheduledUrl,
					data: JSON.stringify(postData),
					dataType: 'json',
					contentType: 'application/json; charset=utf-8',
					async: false						
				}).done(function(data, textStatus, jqXHR) {
					ret = data.response;
				}).fail(function(jqXHR, textStatus, errorThrown) {
					ret = [];
				});
				
				return ret;
			} else {
				return [];
			}
		}
	};
	
	$.fn.quartzCronEditor = function(options) {
		if ( methods[options] ) {
			return methods[ options ].apply( this, Array.prototype.slice.call( arguments, 1 ));
		} else if ( typeof options === 'object' || ! options ) {
			settings = $.extend(settings, options );
			return methods.init.apply( this, arguments );
		} else {
			$.error( 'Method ' +  options + ' does not exist on jQuery.quartzCronEditor' );
		}    
	};
})(jQuery);