$axure.internal(function($ax) {
    $ax.adaptive = {};

    $axure.utils.makeBindable($ax.adaptive, ["viewChanged"]);

    var _auto = true;
    var _autoIsHandledBySidebar = false;

    var _views;
    var _idToView;
    var _enabledViews = [];

    var _initialViewToLoad;
    var _initialViewSizeToLoad;

    var _loadFinished = false;
    $ax.adaptive.loadFinished = function() {
        if(_loadFinished) return;
        _loadFinished = true;
        if($ax.adaptive.currentViewId) $ax.viewChangePageAndMasters();
        else $ax.postAdaptiveViewChanged();
    };

    var _handleResize = function(forceSwitchTo) {
        if(!_auto) return;
        if(_auto && _autoIsHandledBySidebar && !forceSwitchTo) return;

        var $window = $(window);
        var height = $window.height();
        var width = $window.width();

        var toView = _getAdaptiveView(width, height);
        var toViewId = toView && toView.id;

        _switchView(toViewId, forceSwitchTo);
    };

    var _setAuto = $ax.adaptive.setAuto = function(val) {
        if(_auto != val) {
            _auto = Boolean(val);
        }
    };

    var _setLineImage = function(id, imageUrl) {
        var imageQuery = $jobj(id).attr('src', imageUrl);
        if(imageUrl.indexOf(".png") > -1) $ax.utils.fixPng(imageQuery[0]);
    };

    var _switchView = function (viewId, forceSwitchTo) {
        if(!$ax.pageData.isAdaptiveEnabled) return;

        var previousViewId = $ax.adaptive.currentViewId;
        if(typeof previousViewId == 'undefined') previousViewId = '';
        if(typeof viewId == 'undefined') viewId = '';
        if (viewId == previousViewId) {
            if(forceSwitchTo) $ax.postAdaptiveViewChanged(forceSwitchTo);
            return;
        }

        $ax('*').each(function(obj, elementId) {
            if (!$ax.public.fn.IsTreeNodeObject(obj.type)) return;
            if(!obj.hasOwnProperty('isExpanded')) return;

            var query = $ax('#' + elementId);
            var defaultExpanded = obj.isExpanded;

            query.expanded(defaultExpanded);
        });

        // reset all the positioning on the style tags, including size and transformation
        $axure('*').each(function(diagramObject, elementId) {
            var element = document.getElementById(elementId);
            if(element && !diagramObject.isContained) {
                var resetCss = {
                    top: "", left: "", width: "", height: "", opacity: "",
                    transform: "", webkitTransform: "", MozTransform: "", msTransform: "", OTransform: ""
                };
                var query = $(element);
                var children = query.children();
                var sketchyImage = $('#' + $ax.repeater.applySuffixToElementId(elementId, '_image_sketch'));
                var textChildren = query.children('div.text');

                query.css(resetCss);
                if(children) children.css(resetCss);
                if(sketchyImage) sketchyImage.css(resetCss);
                if(textChildren) textChildren.css(resetCss);

                $ax.dynamicPanelManager.resetFixedPanel(diagramObject, element);
                $ax.dynamicPanelManager.resetAdaptivePercentPanel(diagramObject, element);
            }
        });

        $ax.adaptive.currentViewId = viewId; // we need to set this so the enabled and selected styles will apply properly
        if(previousViewId) {
            $ax.style.clearAdaptiveStyles();
            $('*').removeClass(previousViewId);
        } else {
            $ax.style.reselectElements();
        }

        $axure('*').each(function(obj, elementId) {
            $ax.style.updateElementIdImageStyle(elementId); // When image override exists, fix styling/borders
        });

        // reset all the images only if we're going back to the default view
        if(!viewId) {
            _updateInputVisibility('', $axure('*'));
            $axure('*').each(function(diagramObject, elementId) {
                $ax.placeholderManager.refreshPlaceholder(elementId);

                var images = diagramObject.images;
                if(diagramObject.type == 'horizontalLine' || diagramObject.type == 'verticalLine') {
                    var startImg = images['start~'];
                    _setLineImage(elementId + "_start", startImg);
                    var endImg = images['end~'];
                    _setLineImage(elementId + "_end", endImg);
                    var lineImg = images['line~'];
                    _setLineImage(elementId + "_line", lineImg);
                } else if(diagramObject.type == $ax.constants.CONNECTOR_TYPE) {
                    _setAdaptiveConnectorImages(elementId, images, '');
                } else if(images) {
                    if (diagramObject.generateCompound) {

                        if($ax.style.IsWidgetDisabled(elementId)) {
                            disabledImage = _getImageWithTag(images, 'disabled~');
                            if(disabledImage) $ax.style.applyImage(elementId, disabledImage, 'disabled');
                            return;
                        }
                        if($ax.style.IsWidgetSelected(elementId)) {
                            selectedImage = _getImageWithTag(images, 'selected~');
                            if(selectedImage) $ax.style.applyImage(elementId, selectedImage, 'selected');
                            return;
                        }
                        $ax.style.applyImage(elementId, _getImageWithTag(images, 'normal~'));
                    } else {
                        if ($ax.style.IsWidgetDisabled(elementId)) {
                            var disabledImage = $ax.style.getElementImageOverride(elementId, 'disabled') || images['disabled~'];
                            if (disabledImage) $ax.style.applyImage(elementId, disabledImage, 'disabled');
                            return;
                        }
                        if ($ax.style.IsWidgetSelected(elementId)) {
                            var selectedImage = $ax.style.getElementImageOverride(elementId, 'selected') || images['selected~'];
                            if (selectedImage) $ax.style.applyImage(elementId, selectedImage, 'selected');
                            return;
                        }
                        $ax.style.applyImage(elementId, $ax.style.getElementImageOverride(elementId, 'normal') || images['normal~']);
                    }
                }

                var child = $jobj(elementId).children('.text');
                if(child.length) $ax.style.transformTextWithVerticalAlignment(child[0].id, function() { });
            });
            // we have to reset visibility if we aren't applying a new view
            $ax.visibility.resetLimboAndHiddenToDefaults();
            $ax.repeater.refreshAllRepeaters();
            $ax.dynamicPanelManager.updateAllFitPanels();
            $ax.dynamicPanelManager.updatePercentPanelCache($ax('*'));
        } else {
            $ax.visibility.clearLimboAndHidden();
            _applyView(viewId);
            $ax.repeater.refreshAllRepeaters();
        }

        $ax.adaptive.triggerEvent('viewChanged', {});
        if(_loadFinished) $ax.viewChangePageAndMasters(forceSwitchTo);
    };

    var _getImageWithTag  = function(image, tag) {
        var flattened = {};
        for (var component in image) {
            var componentImage = image[component][tag];
            if(componentImage) flattened[component] = componentImage;
        }
        return flattened;
    }

    // gets if input is hidden due to sketch
    var BORDER_WIDTH = "borderWidth";
    var COLOR_STYLE = "colorStyle";
    var SKETCH_FACTOR = "sketchFactor";
    var _areInputsHidden = function(viewId) {
        var chain = _getAdaptiveIdChain(viewId);
        var page = $ax.pageData.page;
        var adaptiveStyles = page.adaptiveStyles;
        // keep track of props that are not sketchy, as you continue to climb up your parents;
        var notSketch = [];
        for(var i = chain.length - 1; i >= -1; i--) {
            var style = i == -1 ? page.style : adaptiveStyles[chain[i]];
            if(notSketch.indexOf(BORDER_WIDTH) == -1 && style.hasOwnProperty(BORDER_WIDTH)) {
                if(style[BORDER_WIDTH] != 0) return true;
                notSketch.push(BORDER_WIDTH);
            }
            if(notSketch.indexOf(COLOR_STYLE) == -1 && style.hasOwnProperty(COLOR_STYLE)) {
                if(style[COLOR_STYLE] != 'appliedColor') return true;
                notSketch.push(COLOR_STYLE);
            }
            if(notSketch.indexOf(SKETCH_FACTOR) == -1 && style.hasOwnProperty(SKETCH_FACTOR)) {
                if(style[SKETCH_FACTOR] != 0) return true;
                notSketch.push(SKETCH_FACTOR);
            }
        }
        return false;
    };

    var _updateInputVisibility = function(viewId, query) {
        var func = _areInputsHidden(viewId) ? 'addClass' : 'removeClass';
        query.each(function(obj, elementId) {
            var input = $jobj($ax.repeater.applySuffixToElementId(elementId, '_input'));
            if(input.length == 0) return;
            input[func]('form_sketch');
        });
    };

    // gets the inheritance chain of a particular view.
    var _getAdaptiveIdChain = $ax.adaptive.getAdaptiveIdChain = function(viewId) {
        if(!viewId) return [];
        var view = _idToView[viewId];
        var chain = [];
        var current = view;
        while(current) {
            chain[chain.length] = current.id;
            current = _idToView[current.baseViewId];
        }
        return chain.reverse();
    };

    var _getPageStyle = $ax.adaptive.getPageStyle = function() {
        var currentViewId = $ax.adaptive.currentViewId;
        var adaptiveChain = _getAdaptiveIdChain(currentViewId);

        var currentStyle = $.extend({}, $ax.pageData.page.style);
        for(var i = 0; i < adaptiveChain.length; i++) {
            var viewId = adaptiveChain[i];
            $.extend(currentStyle, $ax.pageData.page.adaptiveStyles[viewId]);
        }

        return currentStyle;
    };

    var _setAdaptiveLineImages = function(elementId, images, viewIdChain) {
        for(var i = viewIdChain.length - 1; i >= 0; i--) {
            var viewId = viewIdChain[i];
            var startImg = images['start~' + viewId];
            if(startImg) {
                _setLineImage(elementId + "_start", startImg);
                var endImg = images['end~' + viewId];
                _setLineImage(elementId + "_end", endImg);
                var lineImg = images['line~' + viewId];
                _setLineImage(elementId + "_line", lineImg);
                break;
            }
        }
    };

    var _setAdaptiveConnectorImages = function (elementId, images, view) {
        var conn = $jobj(elementId);
        var count = conn.children().length-1; // -1 for rich text panel
        for(var i = 0; i < count; i++) {
            var img = images['' + i + '~' + view];
            $jobj(elementId + '_seg' + i).attr('src', img);
        }
    };

    var _applyView = $ax.adaptive.applyView = function(viewId, query) {
        var limboIds = {};
        var hiddenIds = {};

        var jquery;
        if(query) {
            jquery = query.jQuery();
            jquery = jquery.add(jquery.find('*'));
            var jqueryAnn = $ax.annotation.jQueryAnn(query);
            jquery = jquery.add(jqueryAnn);
        } else {
            jquery = $('*');
            query = $ax('*');
        }
        jquery.addClass(viewId);
        _updateInputVisibility(viewId, query);
        var viewIdChain = _getAdaptiveIdChain(viewId);
        // this could be made more efficient by computing it only once per object
        query.each(function(diagramObject, elementId) {
            _applyAdaptiveViewOnObject(diagramObject, elementId, viewIdChain, viewId, limboIds, hiddenIds);
        });

        $ax.visibility.addLimboAndHiddenIds(limboIds, hiddenIds, query);
        $ax.dynamicPanelManager.updateAllFitPanels();
        $ax.dynamicPanelManager.updatePercentPanelCache(query);
    };

    var _applyAdaptiveViewOnObject = function(diagramObject, elementId, viewIdChain, viewId, limboIds, hiddenIds) {
        var adaptiveChain = [];
        for(var i = 0; i < viewIdChain.length; i++) {
            var viewId = viewIdChain[i];
            var viewStyle = diagramObject.adaptiveStyles[viewId];
            if(viewStyle) {
                adaptiveChain[adaptiveChain.length] = viewStyle;
                if (viewStyle.size) $ax.public.fn.convertToSingleImage($jobj(elementId));
            }
        }

        var state = $ax.style.generateState(elementId);

        // set the image
        var images = diagramObject.images;
        if(images) {
            if(diagramObject.type == 'horizontalLine' || diagramObject.type == 'verticalLine') {
                _setAdaptiveLineImages(elementId, images, viewIdChain);
            } else if (diagramObject.type == $ax.constants.CONNECTOR_TYPE) {
                _setAdaptiveConnectorImages(elementId, images, viewId);
            } else if (diagramObject.generateCompound) {
                var compoundUrl = _matchImageCompound(diagramObject, elementId, viewIdChain, state);
                if (compoundUrl) $ax.style.applyImage(elementId, compoundUrl, state);
            }else {
                var imgUrl = _matchImage(elementId, images, viewIdChain, state);
                if(imgUrl) $ax.style.applyImage(elementId, imgUrl, state);
            }
            //                for(var i = viewIdChain.length - 1; i >= 0; i--) {
            //                    var viewId = viewIdChain[i];
            //                    var imgUrl = $ax.style.getElementImageOverride(elementId, state) || images[state + '~' + viewId] || images['normal~' + viewId];
            //                    if(imgUrl) {
            //                        $ax.style.applyImage(elementId, imgUrl, state);
            //                        break;
            //                    }
            //                }

            //            }
        }
        // addaptive override style (not including default style props)
        var adaptiveStyle = $ax.style.computeAllOverrides(elementId, undefined, state, viewId);

        // this style INCLUDES the object's my style
        var compoundStyle = $.extend({}, diagramObject.style, adaptiveStyle);

        //$ax.style.setAdaptiveStyle(elementId, adaptiveStyle);
        if(!diagramObject.isContained) {
            $ax.style.setAdaptiveStyle(elementId, adaptiveStyle);
        }

        var scriptId = $ax.repeater.getScriptIdFromElementId(elementId);
        if(compoundStyle.limbo && !diagramObject.isContained) limboIds[scriptId] = true;
        // sigh, javascript. we need the === here because undefined means not overriden
        if(compoundStyle.visible === false) hiddenIds[scriptId] = true;
    };

    var _matchImage = function(id, images, viewIdChain, state) {
        var override = $ax.style.getElementImageOverride(id, state);
        if(override) return override;

        if(!images) return undefined;

        // first check all the images for this state
        for(var i = viewIdChain.length - 1; i >= 0; i--) {
            var viewId = viewIdChain[i];
            var img = images[state + "~" + viewId];
            if(img) return img;
        }
        // check for the default state style
        var defaultStateImage = images[state + '~'];
        if(defaultStateImage) return defaultStateImage;

        state = $ax.style.progessState(state);
        if(state) return _matchImage(id, images, viewIdChain, state);

        // SHOULD NOT REACH HERE! NORMAL SHOULD ALWAYS CATCH AT THE DEFAULT!
        return images['normal~']; // this is the default
    };

    var _matchImageCompound = function(diagramObject, id, viewIdChain, state) {
        var images = [];
        for(var i = 0; i < diagramObject.compoundChildren.length; i++) {
            var component = diagramObject.compoundChildren[i];
            images[component] = _matchImage(id, diagramObject.images[component], viewIdChain, state);
        }
        return images;
    };



    $ax.adaptive.getImageForStateAndView = function(id, state) {
        var viewIdChain = _getAdaptiveIdChain($ax.adaptive.currentViewId);
        var diagramObject = $ax.getObjectFromElementId(id);
        if (diagramObject.generateCompound) return _matchImageCompound(diagramObject, id, viewIdChain, state);
        else return _matchImage(id, diagramObject.images, viewIdChain, state);
    };

    var _getAdaptiveView = function(winWidth, winHeight) {
        var _isViewOneGreaterThanTwo = function(view1, view2) {
            return view1.size.width > view2.size.width || (view1.size.width == view2.size.width && view1.size.height > view2.size.height);
        };

        var _isViewOneLessThanTwo = function(view1, view2) {
            var width2 = view2.size.width || 1000000; // artificially large number
            var height2 = view2.size.height || 1000000;

            var width1 = view1.size.width || 1000000;
            var height1 = view1.size.height || 1000000;

            return width1 < width2 || (width1 == width2 && height1 < height2);
        };

        var _isWindowGreaterThanView = function(view, width, height) {
            return width >= view.size.width && height >= view.size.height;
        };

        var _isWindowLessThanView = function(view1, width, height) {
            var viewWidth = view1.size.width || 1000000;
            var viewHeight = view1.size.height || 1000000;

            return width <= viewWidth && height <= viewHeight;
        };

        var greater = undefined;
        var less = undefined;

        for(var i = 0; i < _enabledViews.length; i++) {
            var view = _enabledViews[i];
            if(view.condition == ">=") {
                if(_isWindowGreaterThanView(view, winWidth, winHeight)) {
                    if(!greater || _isViewOneGreaterThanTwo(view, greater)) greater = view;
                }
            } else {
                if(_isWindowLessThanView(view, winWidth, winHeight)) {
                    if(!less || _isViewOneLessThanTwo(view, less)) less = view;
                }
            }
        }
        return less || greater;
    };

    var _isAdaptiveInitialized = function() {
        return typeof _idToView != 'undefined';
    };

    $ax.messageCenter.addMessageListener(function(message, data) {
        //If the adaptive plugin hasn't been initialized yet then 
        //save the view to load so that it can get set when initialize occurs
        if(message == 'switchAdaptiveView') {
            var href = window.location.href.split('#')[0];
            var lastSlash = href.lastIndexOf('/');
            href = href.substring(lastSlash + 1);
            if(href != data.src) return;

            var view = data.view == 'auto' ? undefined : (data.view == 'default' ? '' : data.view);

            if(!_isAdaptiveInitialized()) {
                _initialViewToLoad = view;
            } else _handleLoadViewId(view);
        } else if(message == 'setAdaptiveViewForSize') {
            _autoIsHandledBySidebar = true;
            if(!_isAdaptiveInitialized()) {
                _initialViewSizeToLoad = data;
            } else _handleSetViewForSize(data.width, data.height);
        }
    });

    $ax.adaptive.setAdaptiveView = function(view) {
        var viewIdForSitemapToUnderstand = view == 'auto' ? undefined : (view == 'default' ? '' : view);

        if(!_isAdaptiveInitialized()) {
            _initialViewToLoad = viewIdForSitemapToUnderstand;
        } else _handleLoadViewId(viewIdForSitemapToUnderstand);
    };

    $ax.adaptive.initialize = function() {
        _views = $ax.document.adaptiveViews;
        _idToView = {};

        if(_views && _views.length > 0) {
            for(var i = 0; i < _views.length; i++) {
                var view = _views[i];
                _idToView[view.id] = view;
            }

            var enabledViewIds = $ax.document.configuration.enabledViewIds;
            for(var i = 0; i < enabledViewIds.length; i++) {
                _enabledViews[_enabledViews.length] = _idToView[enabledViewIds[i]];
            }

            if(_autoIsHandledBySidebar && _initialViewSizeToLoad) _handleSetViewForSize(_initialViewSizeToLoad.width, _initialViewSizeToLoad.height);
            else _handleLoadViewId(_initialViewToLoad);
        }

        $axure.resize(function(e) {
            _handleResize();
            $ax.postResize(e); //window resize fires after view changed
        });
    };

    var _handleLoadViewId = function (loadViewId, forceSwitchTo) {
        if(typeof loadViewId != 'undefined') {
            _setAuto(false);
            _switchView(loadViewId != 'default' ? loadViewId : '', forceSwitchTo);
        } else {
            _setAuto(true);
            _handleResize(forceSwitchTo);
        }
    };

    var _handleSetViewForSize = function (width, height) {
        if(!_auto) return;

        var toView = _getAdaptiveView(width, height);
        var toViewId = toView && toView.id;
        _switchView(toViewId);
    };
});