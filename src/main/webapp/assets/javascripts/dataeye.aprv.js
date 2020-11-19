var AprvUtils = {
    getContextPath: function () {
        var offset = location.href.indexOf(location.host) + location.host.length;
        var ctxPath = location.href.substring(offset, location.href.indexOf('/', offset + 1));
        return ctxPath;
    }
};

var AprvDialog = {
    bootboxOpts: {
        title: 'Information',
        message: null,
        backdrop: true,
        callback: function () {
        }
    },
    alert: function (message, opts, type) {
        if (type === null || type === undefined) {
            type = "Information";
        }
        else {
            this.bootboxOpts.title = type;
        }

        if (message === null || message === undefined || typeof(message) !== 'string') {
            return false;
        }

        this.bootboxOpts.message = message;

        if (opts === null || opts === undefined) {
            opts = {};
        }
        else if (typeof(opts) === 'function') {
            this.bootboxOpts.callback = opts;
            opts = {};
        }

        if (typeof(opts) !== 'object') {
            return false;
        }

        var setting = $.extend({}, this.bootboxOpts, opts);
        bootbox.alert(setting).init(this.initAlert(type));
    },
    alertInfo: function (message, opts) {
        this.alert(message, opts, 'Information');
    },
    alertSuccess: function (message, opts) {
        this.alert(message, opts, 'Success');
    },
    alertError: function (message, opts) {
        this.alert(message, opts, 'Error');
    },
    confirm: function (message, callback, opts) {
        if (message === null || message === undefined || typeof(message) !== 'string') {
            return false;
        }

        if (callback === null || callback === undefined || typeof(callback) !== 'function') {
            return false;
        }

        var bootboxOpts = {
            title: 'Confirm',
            message: message,
            buttons: {
                cancel: {
                    label: '<i class="fa fa-close"></i>아니오',
                    className: 'btn-primary-no'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i>예',
                    className: 'btn-primary-ok'
                }
            },
            callback: callback
        };

        if (opts === null || opts === undefined) {
            opts = {};
        }

        if (typeof(opts) !== 'object') {
            return false;
        }

        var setting = $.extend({}, bootboxOpts, opts);        
        bootbox.confirm(setting).init(this.initConfirm());        
    },
    initAlert: function (type) {
        $('.bootbox-alert').children().each(function () {
            if ($(this).hasClass('modal-dialog')) {
                $(this).css('margin-top', '26%');
                $(this).children().each(function () {
                    if ($(this).hasClass('modal-content')) {
                        $(this).children().each(function () {
                            if ($(this).hasClass('modal-footer')) {
                                $(this).remove();
                                return false;
                            }
                        });
                        return false;
                    }
                });
            }
        });

        if (type === 'Information') {
            $('.bootbox-body').append($('<span>').addClass('message_icon_yellow').append($('<i>').addClass('fa fa-exclamation')));
        }
        else if (type === 'Success') {
            $('.bootbox-body').append($('<span>').addClass('message_icon_green').append($('<i>').addClass('fa fa-check')));
        }
        else if (type === 'Error') {
            $('.bootbox-body').append($('<span>').addClass('message_icon_red').append($('<i>').addClass('fa fa-close')));
        }

        $('.bootbox-alert').on('click', function (e) {
            $('bootbox-close-button').click();
        });
    },
    initConfirm: function () {
        $('.bootbox-confirm').children().each(function () {
            if ($(this).hasClass('modal-dialog')) {
                $(this).css('margin-top', '26%');
            }
        });

        $('.bootbox-body').append($('<span>').addClass('message_icon_yellow').append($('<i>').addClass('fa fa-exclamation')));
    }
};

(function ($) {
    $.fn.modalPopup = function (url, data, opts) {    	
        var selector = '#' + $(this).attr('id');
        var ajaxOpts = {
            async: true,
            cache: false,
            type: 'POST',            
            dataType: 'html',
            data: data,
            success: function (data, textStatus, jqXHR) {
                $(selector).html(data);
                $(selector).modal('show');
            },
            timeout: 0
        };
        var setting = $.extend({}, ajaxOpts, opts);
        $.ajax(url, setting);
    };
})(jQuery);

(function($) {
    APRV = {};

    APRV.util = {
        getContextPath: function() {
        	if (DE.contextPath === undefiend) {
        		var offset = location.href.indexOf(location.host) + location.host.length;
                var ctxPath = location.href.substring(offset, location.href.indexOf('/', offset + 1));
                return ctxPath;	
        	} else {
        		return DE.contextPath;
        	}
        }
    };

    APRV.ajax = {
        async: true,
        cache: false,
        type: 'POST',
        dataType: 'json',
        data: {},
        success: function (data, textStatus, jqXHR) {
        },
        timeout: 0,
        sendJson: function (url, data, fnSuccess, opts) {
            var ajaxOpts = {
                async: this.async,
                cache: this.cache,
                type: this.type,
                dataType: this.dataType,
                data: JSON.stringify(data),
                success: fnSuccess,
                contentType: 'application/json; charset=utf-8',
                timeout: this.timeout
            };
            var setting = $.extend({}, ajaxOpts, opts);
            $.ajax(url, setting);
        }
    };

    APRV.ajax.execute = function (url, param, callFunc) {
        var callbacks = $.Callbacks();
        var ret = $.ajax({
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            url: url,
            async: false,
            datatype: 'json',
            data: JSON.stringify(param)
        }).done(function (data, textStatus, jqXHR) {
            if (callFunc !== undefined) {
                callbacks.add(callFunc);
                callbacks.fire(data);
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {

        }).always(function (data, textStatus, jqXHR) {

        });
        return JSON.parse(ret.responseText);
    };

    APRV.open = {};
    APRV.open.aprvPopup = function (aprvId, menuId) {
        var url = AprvUtils.getContextPath() + '/aprv/reqCommonPop';

        var param = {
            'aprvId': aprvId,
            'menuId': menuId
        };

        $('#aprvReqPopModal').modalPopup(url, param);
    };

    APRV.open.aprvDoPopup = function (aprvId, aprvDetlId, menuId) {
        var url = AprvUtils.getContextPath() + '/aprv/doCommonPop';
    	
        var param = {
            'aprvId': aprvId,
            'aprvDetlId': aprvDetlId,
            'menuId': menuId
        };
        
        $('#aprvDoPopModal').modalPopup(url, param);
    };

    APRV.open.aprvTotPopup = function () {
        var $frm = $('#_aprvTotForm');
        var url = AprvUtils.getContextPath() + '/aprv/totCommonPop';
        var options = 'width=1000, height=677, toolbar=no, menubar=no, location=no';

        var winId = window.open('about:blank', 'aprv_tot', options);

        if ($frm.length === 0) {
            $frm = $('<form id="_aprvTotForm" name="_aprvTotForm" method="POST"></form>').css('display', 'none');
            $frm.attr('action', url);
            $frm.attr('target', 'aprv_tot');
            $frm.appendTo('body');
        }

        $frm.submit();
        $frm.remove();
        winId.focus();
    };

    APRV.open.aprvStsPopup = function (aprvId, menuId) {
        var $frm = $('#_aprvStsForm');
        var url = AprvUtils.getContextPath() + '/aprv/stsCommonPop';
        var options = 'width=1000, height=527, toolbar=no, menubar=no, location=no';

        var winId = window.open('about:blank', 'aprv_sts', options);

        if ($frm.length === 0) {
            $frm = $('<form id="_aprvTotForm" name="_aprvTotForm" method="POST"></form>').css('display', 'none');
            $frm.attr('action', url);
            $frm.attr('target', 'aprv_sts');
            $frm.appendTo('body');
        }

        var $aprvId = $('<input type="hidden" id="aprvId" name="aprvId" />');
        $aprvId.attr('value', aprvId);
        $aprvId.appendTo($frm);

        var $menuId = $('<input type="hidden" id="menuId" name="menuId" />');
        $menuId.attr('value', menuId);
        $menuId.appendTo($frm);

        $frm.submit();
        $frm.remove();
        winId.focus();
    };

    APRV.fn = {};

    APRV.fn.gsonDecode = function (html) {
        if (APRV.fn.isempty(html)) {
            return '';
        }

        return decodeURIComponent(html);
    };

    APRV.fn.guid = {
        s4: function () {
            return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
        },
        get: function () {
            return this.s4() + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + '-' + this.s4() + this.s4() + this.s4();
        }
    };

    APRV.fn.isempty = function (obj) {
        if (obj === undefined || obj === null || obj === '' || (obj !== null && typeof obj === 'object' && !Object.keys(obj).length)) {
            return true;
        }

        return false;
    };

    APRV.fn.escape = function (str) {
        return str.replace(/[\b]/g, '\\b')
            .replace(/[\f]/g, '\\f')
            .replace(/[\n]/g, '\\n')
            .replace(/[\r]/g, '\\r')
            .replace(/[\t]/g, '\\t');
    };

    APRV.fn.nl2br = function (str) {
        return str.replace(/[\n]/g, '<br />');
    };

    APRV.fn.numberWithCommas = function (num) {
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };

    APRV.fn.byteCheck = function (obj, byteCount, maxByte) {
        $(obj).on('keyup', function () {
            var byteTxt = '';
            var byte = function (el) {
                var codeByte = 0;
                for (var idx = 0, len = el.length; idx < len; idx++) {
                    var oneChar = encodeURIComponent(el.charAt(idx));

                    if (oneChar.length === 1) {
                        codeByte++;
                    }
                    else if (oneChar.indexOf('%u') !== -1) {
                        codeByte += 3;
                    }
                    else if (oneChar.indexOf('%') !== -1) {
                        codeByte++;
                    }

                    if (codeByte <= maxByte) {
                        byteTxt += el.charAt(idx);
                    }
                }

                return codeByte;
            };

            if (byte($(this).val()) > maxByte) {
                AprvDialog.alertInfo(maxByte + 'byte를 초과하여 입력할 수 없습니다.');
                $(this).val(byteTxt);
            }

            if (byteCount !== null && byteCount !== '') {
                $(byteCount).html(APRV.fn.numberWithCommas(byte($(this).val())));
            }
        });

        $(obj).trigger('keyup');
    };

    APRV.fn.byteCheckYn = function (obj, maxByte) {
        var byteTxt = '';
        var byte = function (el) {
            var codeByte = 0;
            for (var idx = 0, len = el.length; idx < len; idx++) {
                var oneChar = encodeURIComponent(el.charAt(idx));

                if (oneChar.length === 1) {
                    codeByte++;
                }
                else if (oneChar.indexOf('%u') !== -1) {
                    codeByte += 3;
                }
                else if (oneChar.indexOf('%') !== -1) {
                    codeByte++;
                }

                if (codeByte <= maxByte) {
                    byteTxt += el.charAt(idx);
                }
            }

            return codeByte;
        };

        if (byte($(obj).val()) > maxByte) {
            $(obj).val(byteTxt);
            return false;
        }

        return true;
    };

    APRV.fn.htmlEncode = function (value) {
        return $('<div/>').text(value).html();
    };

    APRV.fn.htmlDecode = function (value) {
        return $('<div/>').html(value).text();
    };

    APRV.fn.fileUpload = function (objId, gridId, frowId) {
        $(objId).fileupload({
            url: AprvUtils.getContextPath() + '/file/blueimp/uploads.json',
            dataType: 'json',
            singleFileUploads: false,
            add: function (e, data) {
                if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
                    var maxSize = 1024 * 1024 * 30;
                    var maxCount = 10;
                    var isError = false;
                    var cnt = $(gridId).getGridParam('reccount');
                    $.each(data.files, function (index, file) {
                        cnt++;
                        if (file.size > maxSize) {
                            AprvDialog.alertInfo('최대 파일 크기는 ' + (maxSize/1024/1024) + 'MB 입니다.\n(' + file.name + ' 파일 크기 ' + (file.size/1024/1024).toFixed(1) + 'MB)');
                            isError = true;
                            return false;
                        }

                        if (file.type === '') {
                            return false;
                        }
                    });

                    if (!isError && cnt > maxCount) {
                        AprvDialog.alertInfo('최대 첨부 파일 수는 ' + maxCount + '개 입니다.');
                        isError = true;
                        return false;
                    }

                    if (!isError) {
                        data.process().done(function () {
                            data.submit();
                        });
                    }
                }
            },
            done: function (e, data) {
                var ids = $(gridId).jqGrid('getDataIDs');
                var rowIndex = 0;
                if (!APRV.fn.isempty(ids)) {
                    rowIndex = Math.max.apply(Math, ids);
                }

                if (data.result.resultCd === 'S000') {
                    $.each(data.result.resultData, function (index, file) {
                        if (!APRV.fn.isempty(frowId)) {
                            file.aprvFileNew = 'Y';
                            $(gridId).jqGrid('addRowData', ++rowIndex, file, 'before', frowId);
                        }
                        else {
                            file.aprvFileNew = 'Y';
                            $(gridId).jqGrid('addRowData', ++rowIndex, file, 'last');
                        }
                    });
                }
                else if (data.result.resultCd === 'I100') {
                    AprvDialog.alertInfo(data.result.resultNm);
                }
                else {
                    AprvDialog.alertError(data.result.resultNm);
                }
            }
        }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
    };

    APRV.jqgrid = {};
    APRV.jqgrid.checkAll = function (source, e) {
        e = e || event;
        e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;

        var grid = $(source).closest('.ui-jqgrid-view').find('.ui-jqgrid-bdiv table.ui-jqgrid-btable');
        var CHK = $(source).is(':checked');

        if (CHK) {
            grid.jqGrid('setLabel', 'CHK', '<input type="checkbox" id="' + $(source).prop('id') + '" name="' + $(source).prop('name') + '" onclick="javascript:APRV.jqgrid.checkAll(this, event);" checked />');
        }
        else {
            grid.jqGrid('setLabel', 'CHK', '<input type="checkbox" id="' + $(source).prop('id') + '" name="' + $(source).prop('name') + '" onclick="javascript:APRV.jqgrid.checkAll(this, event);" />');
        }

        var chkbox = $('tr.jqgrow > td > input[type=checkbox]', grid[0]);

        for (var i = 0, len = chkbox.length; i < len; i++) {
            var rowid = chkbox.eq(i).closest('tr').prop('id');
            var row = grid.getLocalRow(rowid);

            if (CHK) {
                row['CHK'] = true.toString();
                chkbox.eq(i).prop('checked', true);
            }
            else {
                row['CHK'] = false.toString();
                chkbox.eq(i).prop('checked', false);
            }
        }
    };

    APRV.jqgrid.checkedHandler = function (grid, chk) {
        if (APRV.fn.isempty(chk))
            chk = 'CHK';

        grid.find('input[type=checkbox]').off('click');
        grid.find('input[type=checkbox]').on('click', function (e) {
            var rowid = $(e.target).closest('tr').prop('id');
            var row = grid.getLocalRow(rowid);
            row[chk] = $(e.target).is(':checked').toString();
        });
    };
})(jQuery);