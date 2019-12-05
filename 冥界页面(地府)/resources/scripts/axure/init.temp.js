$axure.internal(function($ax) {

    $(window.document).ready(function() {
        var readyStart = (new Date()).getTime();

        //this is because the page id is not formatted as a guid
        var pageId = $ax.pageData.page.packageId;

        var pageData = {
            id: pageId,
            pageName: $ax.pageData.page.name,
            location: window.location.toString(),
            notes: $ax.pageData.page.notes
        };

        var anns = [];
        $ax('*').each(function (dObj, elementId) {
            pushAnnotation(dObj, elementId);
        });

        function pushAnnotation(dObj, elementId) {
            var ann = dObj.annotation;
            if(ann) {
                ann["id"] = elementId;
                ann["label"] = dObj.label + " (" + dObj.friendlyType + ")";
                anns.push(ann);
            }

            if(dObj.type == 'repeater') {
                if(dObj.objects) {
                    for(var i = 0, len = dObj.objects.length; i < len; i++) {
                        pushAnnotation(dObj.objects[i]);
                    }
                }
            }
        }

        pageData.widgetNotes = anns;

        //only trigger the page.data setting if the window is on the mainframe
        var isMainFrame = false;
        try {
            if(window.name == 'mainFrame' ||
            (!CHROME_5_LOCAL && window.parent.$ && window.parent.$('#mainFrame').length > 0)) {
                isMainFrame = true;

                $ax.messageCenter.addMessageListener(function(message, data) {
                    if(message == 'finishInit') {
                        _processTempInit();
                    }
                });

                $axure.messageCenter.setState('page.data', pageData);
                window.focus();
            }
        } catch(e) { }

        //attach here for chrome local
        $(window).load(function() {
            $ax.style.initializeObjectTextAlignment($ax('*'));
        });

        if(!isMainFrame) _processTempInit();
    });


    var _processTempInit = function() {
        //var start = (new Date()).getTime();
        //var end = (new Date()).getTime();
        //window.alert('elapsed ' + (end - start));

        $('iframe').each(function() {
            var origSrc = $(this).attr('basesrc');

            var $this = $(this);
            if(origSrc) {
                var newSrcUrl = origSrc.toLowerCase().indexOf('http://') == -1 ? $ax.globalVariableProvider.getLinkUrl(origSrc) : origSrc;
                $this.attr('src', newSrcUrl);
            }

            if(IOS) {
                $this.parent().css('overflow', 'auto').css('-webkit-overflow-scrolling', 'touch').css('-ms-overflow-x', 'hidden').css('overflow-x', 'hidden');
            }
        });

        $axure.messageCenter.addMessageListener(function(message, data) {
            if(message == 'setGlobalVar') {
                $ax.globalVariableProvider.setVariableValue(data.globalVarName, data.globalVarValue, true);
            }
        });

        window.lastFocusedClickable = null;
        var _lastFocusedClickableSelector = 'div[tabIndex=0], img[tabIndex=0], a';
        var shouldOutline = true;

        $ax(function (dObj) { return dObj.tabbable; }).each(function (dObj, elementId) {
            if ($ax.public.fn.IsLayer(dObj.type)) $ax.event.layerMapFocus(dObj, elementId);
            var focusableId = $ax.event.getFocusableWidgetOrChildId(elementId);
            $('#' + focusableId).attr("tabIndex", 0);
        });

        $('div[tabIndex=0], img[tabIndex=0]').bind($ax.features.eventNames.mouseDownName, function() {
            shouldOutline = false;
        });

        $(window.document).bind($ax.features.eventNames.mouseUpName, function() {
            shouldOutline = true;
        });

        $(_lastFocusedClickableSelector).focus(function () {
            if(shouldOutline) {
                $(this).css('outline', '');
            } else {
                $(this).css('outline', 'none');
            }

            window.lastFocusedClickable = this;
        });

        $(_lastFocusedClickableSelector).blur(function () {
            if(window.lastFocusedClickable == this) window.lastFocusedClickable = null;
        });

        $(window.document).bind('keyup', function(e) {
            if(e.keyCode == '13' || e.keyCode == '32') {
                if(window.lastFocusedClickable) $(window.lastFocusedClickable).click();
            }
        });

        if($ax.document.configuration.hideAddress) {
            $(window).load(function() {
                window.setTimeout(function() {
                    window.scrollTo(0, 0.9);
                }, 0);
            });
        }

        if($ax.document.configuration.preventScroll) {
            $(window.document).bind('touchmove', function(e) {
                var inScrollable = $ax.legacy.GetScrollable(e.target) != window.document.body;
                if(!inScrollable) {
                    e.preventDefault();
                }
            });

            $ax(function(diagramObject) {
                return $ax.public.fn.IsDynamicPanel(diagramObject.type) && diagramObject.scrollbars != 'none';
            }).$().children().bind('touchstart', function() {
                var target = this;
                var top = target.scrollTop;
                if(top <= 0) target.scrollTop = 1;
                if(top + target.offsetHeight >= target.scrollHeight) target.scrollTop = target.scrollHeight - target.offsetHeight - 1;
            });
        }

        if(OS_MAC && WEBKIT) {
            $ax(function(diagramObject) {
                return $ax.public.fn.IsComboBox(diagramObject.type);
            }).each(function(obj, id) {
                $jobj($ax.INPUT(id)).css('-webkit-appearance', 'menulist-button').css('border-color', '#999999');
            });
        }

        $ax.legacy.BringFixedToFront();
        $ax.event.initialize();
        $ax.style.initialize();
        $ax.visibility.initialize();
        $ax.repeater.initialize();
        $ax.dynamicPanelManager.initialize(); //needs to be called after visibility is initialized
        $ax.adaptive.initialize();
        $ax.loadDynamicPanelsAndMasters();
        $ax.adaptive.loadFinished();
        var start = (new Date()).getTime();
        $ax.repeater.initRefresh();
        var end = (new Date()).getTime();
        console.log('loadTime: ' + (end - start) / 1000);
        $ax.style.prefetch();

        $(window).resize();

        //var readyEnd = (new Date()).getTime();
        //window.alert('elapsed ' + (readyEnd - readyStart));
    };
});

/* extend canvas */
var gv_hasCanvas = false;
(function() {
    var _canvas = document.createElement('canvas'), proto, abbrev;
    if(gv_hasCanvas = !!(_canvas.getContext && _canvas.getContext('2d')) && typeof (CanvasGradient) !== 'undefined') {
        function chain(func) {
            return function() {
                return func.apply(this, arguments) || this;
            };
        }

        with(proto = CanvasRenderingContext2D.prototype) for(var func in abbrev = {
            a: arc,
            b: beginPath,
            n: clearRect,
            c: clip,
            p: closePath,
            g: createLinearGradient,
            f: fill,
            j: fillRect,
            z: function(s) { this.fillStyle = s; },
            l: lineTo,
            w: function(w) { this.lineWidth = w; },
            m: moveTo,
            q: quadraticCurveTo,
            h: rect,
            r: restore,
            o: rotate,
            s: save,
            x: scale,
            y: function(s) { this.strokeStyle = s; },
            u: setTransform,
            k: stroke,
            i: strokeRect,
            t: translate
        }) proto[func] = chain(abbrev[func]);
        CanvasGradient.prototype.a = chain(CanvasGradient.prototype.addColorStop);
    }
})();
