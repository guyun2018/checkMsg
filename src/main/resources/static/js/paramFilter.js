(function($){

	$.fn.changeParamFilter=function(paramDiv){
		
		var maintypeArr=[];
		var centerArr=[];
		var brandArr=[];
		var sexArr=[];
		var yearArr=[];
		var seasonArr=[];
		
		$('.maintype-span .selected-param',paramDiv).each(function(){
			maintypeArr.push($(this).attr("title"));
		});
		
		$('.center-span .selected-param',paramDiv).each(function(){
			centerArr.push($(this).attr("title"));
		});
		$('.brand-span .selected-param',paramDiv).each(function(){
			brandArr.push($(this).attr("title"));
		});
		$('.sex-span .selected-param',paramDiv).each(function(){
			sexArr.push($(this).attr("title"));
		});
		$('.year-span .selected-param',paramDiv).each(function(){
			yearArr.push($(this).attr("title"));
		});
		$('.season-span .selected-param',paramDiv).each(function(){
			seasonArr.push($(this).attr("title"));
		});
		var spreadWayId=$("#spreadWayId",paramDiv).val();
		
		var changeData={ 
		  		"maintypeArray":maintypeArr,
		  		"centerArray":centerArr,
		  		"brandArray" : brandArr,
		  		"sexArray" : sexArr,
		  		"yearArray" : yearArr,
		  		"seasonArray" : seasonArr,
		  		"spreadWay" : spreadWayId
	    };
		//关联查询
		$.ajax({
			  url: getRootPath()+"/commonAttribute/searchProAttribute",
			  type : "POST",
			  dataType: "json",
			  data: JSON.stringify(changeData),
              contentType:"application/json; charset=utf-8",
			  success: function( data ) {
				var seriesArr=data["seriesArray"];
				var modelArr=data["modelArray"];
				var saleWayArr=data["saleWayArray"];
				var shopArr=data["shopArray"];
				
				$(".advance-param-list .advance-param-span",paramDiv).hide();
				
				for(var i in saleWayArr){
					var id="saleWay"+saleWayArr[i];
					$("#"+id,paramDiv).show();
				}
				
				for(var i in modelArr){
					var id="model"+modelArr[i];
					$("#"+id,paramDiv).show();
				}
				for(var i in seriesArr){
					var id="series"+seriesArr[i];
					$("#"+id,paramDiv).show();
				}
				for(var i in shopArr){
					var id="shop"+shopArr[i];
					$("#"+id,paramDiv).show();
				}
				
				$(".advance-param-list > div > span:hidden",paramDiv).removeClass("selected-param");
			}
		});
	
	};
	
	$.fn.initParamFilterHtml=function(paramDiv,showArray,btnDivId){
		//生成基本DOM
	    var conditionHtml='<div id="param-selected-list" class="input-group param-selected-list  param-filter">'+
                '<label id="param-all-check" class="param-all-check" style="cursor: pointer;font-size:14px;line-height:27px;" title="清空所有条件">'+
                    '条件&nbsp;：&nbsp;</label>'+
                '<label id="maintype-span" class="maintype-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">品类:</label>'+
                '<label id="center-span" class="center-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">中心:</label>'+
                '<label id="brand-span" class="brand-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">品牌:</label>'+
                '<label id="sex-span" class="sex-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">性别:</label>'+
                '<label id="year-span" class="year-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">年份:</label>'+
                '<label id="season-span" class="season-span sxtj_box" style="margin-left: 10px; display: none;color:#999;">季节:</label>'+
            '</div>'+
            '<div id="advance-param-list" class="advance-param-list  param-filter" style="margin-left:5px;display:none;">'+
                '<div class="saleWayList input-group" id="saleWayList">'+
                    '<label class="search-addon saleWay-clear" style="cursor: pointer;color:#999;" title="清空已选条件">销售策略：</label>'+
                '</div>'+
                '<div class="modelList input-group" id="modelList">'+
                    '<label class="search-addon model-clear" style="cursor: pointer;color:#999;" title="清空已选条件">款型：</label>'+
                '</div>'+
                '<div class="seriesList input-group" id="seriesList">'+
                    '<label class="search-addon series-clear" style="cursor: pointer;color:#999;" title="清空已选条件">系列：</label>'+
                '</div>'+
                '<div class="shopList input-group" id="shopList">'+
                    '<label class="search-addon shop-clear" style="cursor: pointer;color:#999;" title="清空已选条件">销售店铺：</label>'+
                '</div>'+
            '</div>';
	    //<!-- 弹出框内容 -->
	    var paramHtml='<div id="param-list" class="param-list param-filter" style="width: 100%; margin-top: 10px; display: none;">'+
             '<div id="maintypeBoxList" class="maintypeBoxList input-group"></div>'+ 
             '<div id="sexBoxList" class="sexBoxList input-group"></div>'+
             '<div id="yearBoxList" class="yearBoxList input-group"></div>'+
             '<div id="seasonBoxList" class="seasonBoxList input-group"></div>'+
             '<div id="brandBoxList" class="brandBoxList input-group"></div>'+
             '<div id="centerBoxList" class="centerBoxList input-group"></div>'+
         '</div>'+
         '<div class="spuModelPop-div"  style="display:none;" >'+
             '<div class="input-group" name="paramSpuList">'+
                 '<input type="text" id="paramModelName" name="modelName" value="新筛选模板" style="margin-right: 10px;height: 25px;" />'+   
                 '<button  class="model-save-btn btn btn-xs btn-primary" > 保存</button>'+
              '</div>'+
          '</div>';
	    //弹出框触发按钮
	    var btnHtml='<div class="param-btn-list searchBlock" style="width:initial;">'+
	        '<button style="font-size: 5px; margin-right: 10px;float: left;"id="maintype-popover" name="maintype-span"'+ 
                'class="maintype-popover btn-param-pop btn-xs btn_n"> 品类&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 5px; margin-right: 10px;float: left;"id="center-popover" name="center-span" '+
                'class="center-popover btn-param-pop btn-xs btn_n"> 中心&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 5px; margin-right: 10px;float: left;"id="brand-popover" name="brand-span" '+
                'class="brand-popover btn-param-pop btn-xs btn_n"> 品牌&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 5px; margin-right: 10px;float: left;"id="sex-popover" name="sex-span" '+
                'class="sex-popover btn-param-pop btn-xs btn_n"> 性别&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 5px; margin-right: 10px;float: left;"id="year-popover" name="year-span"'+
                'class="year-popover btn-param-pop btn-xs btn_n"> 年份&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 5px; margin-right: 10px;float: left;"id="season-popover" name="season-span"'+
                'class="season-popover btn-param-pop btn-xs btn_n"> 季节&nbsp;<i class="fa fa-angle-down"></i>'+
            '</button>'+
            '<button style="font-size: 14px; margin-right: 0px;float: left;"id="show-advance-param"'+
                'class="show-advance-param btn btn_s3 btn_blue2"> 更多&nbsp;<i class="fa fa-angle-double-down"></i>'+
            '</button></div>';
	    btnDivId="param-btn-div";
	    $("."+btnDivId,paramDiv).prepend(btnHtml);
	    paramDiv.prepend(conditionHtml);
	    paramDiv.append(paramHtml);
	    
	    var loadModelList=true; //是否加载 模板列表
        for(var i in showArray){
            var showBean=showArray[i];
            if(showBean["show"]){
                $("."+showBean["clsName"],paramDiv).show();
            }else{
                loadModelList=showBean["clsName"]=="param-model-manage"?false:true;
                $("."+showBean["clsName"],paramDiv).hide();
            }
        }
	    
	//	var paramDiv=$(this);
		//初始化 参数列表 -- 批量处理
		$.ajax({
			url: getRootPath()+"/commonAttribute/getProAttributeJson",
			dataType: "json",
			data: { },
			success: function( data ) {
				
				//弹出框
				//品牌加入到表格中 优化排版
				var paramHtml="";
				for(var i in data["center"]){
					var bean=data["center"][i];
					paramHtml+="<span id='center"+bean.code+"' title='"+bean.code+"' name='center-span' class='label-css'  >"+bean.name+"</span>";
					
				}
				$(".centerBoxList",paramDiv).append(paramHtml);
				
				var paramHtml="<table>";
				for(var i in data["maintype"]){
					var bean=data["maintype"][i];
					paramHtml+="<tr><td><span id='maintype"+bean.code+"' title='"+bean.code+"' name='maintype-span' class='label-css'  >"+bean.name+"</span></td></tr>";
					
				}
				paramHtml+="</table>";
				$(".maintypeBoxList",paramDiv).append(paramHtml);
				
				var paramHtml="<table>";
				for(var i in data["brand"]){
					var bean=data["brand"][i];
					paramHtml+="<tr><td><span id='brand"+bean.code+"' title='"+bean.code+"' name='brand-span' class='label-css'  >"+bean.name+"</span></td></tr>";
					
				}
				paramHtml+="</table>";
				$(".brandBoxList",paramDiv).append(paramHtml);
				
				var paramHtml="";
				for(var i in data["sex"]){
					var bean=data["sex"][i];
					paramHtml+="<span id='sex"+bean.code+"' title='"+bean.code+"' name='sex-span' class='label-css'  >"+bean.name+"</span>";
					
				}
				$(".sexBoxList",paramDiv).append(paramHtml);
				
				var paramHtml="<table>";
				for(var i in data["year"]){
					var bean=data["year"][i];
					paramHtml+="<tr><td><span id='year"+bean.code+"' title='"+bean.code+"' name='year-span' class='label-css'  >"+bean.name+"</span></td></tr>";
					
				}
				paramHtml+="</table>";
				$(".yearBoxList",paramDiv).append(paramHtml);
				
				var paramHtml="";
				for(var i in data["season"]){
					var bean=data["season"][i];
					paramHtml+="<span id='season"+bean.code+"' title='"+bean.code+"' name='season-span' class='label-css'  >"+bean.name+"</span>";
				}
				$(".seasonBoxList",paramDiv).append(paramHtml);
				  
				//高级查询属性列表框
				var paramHtml="";
				for(var i in data["saleWay"]){
					var bean=data["saleWay"][i];
					paramHtml+="<span id='saleWay"+bean.code+"' title='"+bean.code+"' name='saleWay-span'  class='label-css advance-param-span'  >"+bean.name+"</span>";
				}
				$(".saleWayList",paramDiv).append(paramHtml);
				
				var paramHtml="";
				for(var i in data["model"]){
					var bean=data["model"][i];
					paramHtml+="<span id='model"+bean.code+"' title='"+bean.code+"' name='model-span'  class='label-css advance-param-span'  >"+bean.name+"</span>";
				}
				$(".modelList",paramDiv).append(paramHtml);
				
				var paramHtml="";
				for(var i in data["series"]){
					  var bean=data["series"][i];
					  paramHtml+="<span id='series"+bean.code+"' title='"+bean.code+"' name='series-span'  class='label-css advance-param-span'  >"+bean.name+"</span>";
				}
				$(".seriesList",paramDiv).append(paramHtml);
				
				var paramHtml="";
				for(var i in data["shop"]){
					var bean=data["shop"][i];
					paramHtml+="<span id='shop"+bean.code+"' title='"+bean.code+"' name='shop-span'  class='label-css advance-param-span'  >"+bean.name+"</span>";
				}
				$(".shopList",paramDiv).append(paramHtml);
				
				
				var paramHtml="<option value=''  >所有</option>";
				for(var i in data["supplier"]){
					  var bean=data["supplier"][i];
					  paramHtml+="<option value='"+bean.code+"' >"+bean.name+"</option>";
				}
				$(".supplier-select",paramDiv).html(paramHtml);
				
				
				//条件选择弹出框
				var popOption={
							html:true,
							placement:'bottom',
							animation:true,
							content : $(".maintypeBoxList",paramDiv).html()	
				};
					
				$('.maintype-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".centerBoxList",paramDiv).html();
				$('.center-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".brandBoxList",paramDiv).html();
				$('.brand-popover',paramDiv).popover(popOption);
					
				popOption.content=$(".sexBoxList",paramDiv).html();
				$('.sex-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".yearBoxList",paramDiv).html();
				$('.year-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".seasonBoxList",paramDiv).html();
				$('.season-popover',paramDiv).popover(popOption);
				
				//更多条件 点击切换选中状态
				$(".advance-param-span",paramDiv).on("click",function(){
					//二级筛选条件 切换样式
					$(this).toggleClass("selected-param");
				});		
				
			  }
		  });
		
		$(".show-advance-param",paramDiv).click(function(){
			$(".advance-param-list",paramDiv).slideToggle("fast");
			if ($(".show-advance-param",paramDiv).text().indexOf("更多") >= 0) {
				$(".show-advance-param",paramDiv).html("收起&nbsp;<i class='fa fa-angle-double-up'/>");
			} else {
				$(".show-advance-param",paramDiv).html("更多&nbsp;<i class='fa fa-angle-double-down'></i>");
			}
			
		});
		
		//批量清空 绑定 到点击事件
		$(".saleWay-clear",paramDiv).click(function(){
			$(".saleWayList .selected-param",paramDiv).toggleClass("selected-param");
		});
		$(".model-clear",paramDiv).click(function(){
			$(".modelList .selected-param",paramDiv).toggleClass("selected-param");
		});
		$(".series-clear",paramDiv).click(function(){
			$(".seriesList .selected-param",paramDiv).toggleClass("selected-param");
		});
		$(".shop-clear",paramDiv).click(function(){
			$(".shopList .selected-param",paramDiv).toggleClass("selected-param");
		});
		
		
		$(".param-all-check",paramDiv).click(function(){
			$(".selected-param",paramDiv).toggleClass("selected-param");
			
			$(".param-selected-list > label",paramDiv).hide();
			$(".param-all-check",paramDiv).show();
			$(".param-selected-list > label > span",paramDiv).remove();
			
			$('.advance-param-list > div > span',paramDiv).show();
			
		});
		
		
		//绑定下拉框 事件
		$(".drag-list",paramDiv).change(function(){
			$.fn.changeParamFilter(paramDiv);
		});
		
		//为每个弹出按钮绑定 相关处理方法
		$(".param-btn-list > button",paramDiv).each(function(){
			//绑定 弹框加载完毕时间  
			$(this).on('show.bs.popover', function(){
				$(".param-btn-list .popover",paramDiv).css('z-index','-1');
				
				//设置弹出框层级，保证当前弹出框位于最上层
				$(this).next(".param-btn-list .popover",paramDiv).css('z-index','1000');
		
				//其中一个弹出框点击弹出，其他弹出框自动隐藏
				$(".param-btn-list > button",paramDiv).each(function(){
					$(this).popover('hide');
			  	});
			});
		
			
			$(this).on('shown.bs.popover', function () {
				var _this=$(this);
				//追加隐藏 事件  当鼠标离开 弹出框
				_this.next(".popover").on("mouseleave",function(){
					$(this).css('z-index','-1');
					_this.popover('hide');
				});
				//判断条件弹出框位置
				var btnName=$(this).attr("name");
				//在弹出框中 高亮当前已选中的产品属性
				$(".param-list .selected-param[name="+btnName+"]:hidden",paramDiv).each(function(){
					//取到隐藏属性列已已选中（高亮）的 span 
					
					var title=$(this).attr("title");
					//让弹框中的条件保持同步高亮
					$(".param-btn-list .popover-content span[name="+btnName+"][title="+title+"]",paramDiv).toggleClass("selected-param");
				});
				
				//绑定弹出框事件
				$(".param-btn-list .popover-content span",paramDiv).click(function(){
					$(this).toggleClass("selected-param");
					var text=$(this).text().trim();
					var spanId=$(this).attr("name");
					var title=$(this).attr("title");
					
					$(".param-list .input-group span[name='"+spanId+"'][title='"+title+"']",paramDiv).toggleClass("selected-param");
					
					var paramId=spanId+"-"+title;
					if($("#"+paramId,paramDiv).length>0){
						
						$("#"+paramId,paramDiv).remove();
						
					}else{
						//往条件行 添加已选中的条件
						var spanStr="<span id='"+paramId+"' class='selected-param' title='"+$(this).attr("title")+"' name='"+spanId+"' style='margin-left:10px;' >"+text+"</span>";
						$("#"+spanId,paramDiv).append(spanStr);
						$("#"+paramId,paramDiv).click(function(){
							var spanId=$(this).attr("name");
							var title=$(this).attr("title");
							$(".param-list .input-group span[name='"+spanId+"'][title='"+title+"']",paramDiv).toggleClass("selected-param");
							$(".param-btn-list .popover-content span[name='"+spanId+"'][title='"+title+"']",paramDiv).toggleClass("selected-param");
							
							
							$(this).remove();
							//条件联动
							$.fn.changeParamFilter(paramDiv);
							
							
							if($("#"+spanId,paramDiv).text().length<4){
							$("#"+spanId,paramDiv).hide();
							}
						});
					}
					
					//条件联动
					$.fn.changeParamFilter(paramDiv);
					
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}else{
						$("#"+spanId,paramDiv).hide();
					}
				});
				
			});
			
		});
	};	
	
	
	$.fn.getParamFilterResultJson=function(paramDiv){
		//该部分是产品搜索
		var maintypeArray=[];
		var centerArray=[];
		var brandArray=[];
		var sexArray=[];
		var yearArray=[];
		var seasonArray=[];
		var saleWayArray=[];
		var modelArray=[];
		var seriesArray=[];
		var productShopArray=[];
		var spreadWay='';
		var supplier='';
		var isOld='';
		var applyType=0;
		var checkFlag='';
		var spu='';
		var showRecycle='';
		var operationStatus='';
		var nodeId='';
		var deleFlag='';
		
		var paramCnt=0;
		
		$('.maintype-span .selected-param',paramDiv).each(function(){
			maintypeArray.push($(this).attr("title"));
			paramCnt++;
		});
		$('.center-span .selected-param',paramDiv).each(function(){
			centerArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		$('.brand-span .selected-param',paramDiv).each(function(){
			brandArray.push($(this).attr("title"));
			paramCnt++;
		});
		$('.sex-span .selected-param',paramDiv).each(function(){
			sexArray.push($(this).attr("title"));
			paramCnt++;
		});
		$('.year-span .selected-param',paramDiv).each(function(){
			yearArray.push($(this).attr("title"));
			paramCnt++;
		});
		$('.season-span .selected-param',paramDiv).each(function(){
			seasonArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		$('.saleWayList .selected-param',paramDiv).each(function(){
			saleWayArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		$('.modelList .selected-param',paramDiv).each(function(){
			modelArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		$('.seriesList .selected-param',paramDiv).each(function(){
			seriesArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		$('.shopList .selected-param',paramDiv).each(function(){
			productShopArray.push($(this).attr("title"));
			paramCnt++;
		});
		
		var data=$("#searchForm").serializeJson();
        if($("#showRecycle").data("status")=="list"){
            showRecycle=false;
        }else{
            showRecycle=true;
        }
        spreadWay=data.spreadWayId;
        spu=data.spu;
        supplier=data.supplierId;
        checkFlag=data.checkFlag;
        isOld=data.isOld;
        applyType=data.applyType;
		paramCnt=spreadWay==''?paramCnt:paramCnt++;
		paramCnt=supplier==''?paramCnt:paramCnt++;
		nodeId=data.nodeId;
		operationStatus=data.operationStatus;
		deleFlag=data.deleFlag*1;
		
		return {
				"maintypeArray" : maintypeArray,
				"centerArray":centerArray,
				"brandArray":brandArray,
				"sexArray":sexArray,
				"yearArray":yearArray,
				"seasonArray":seasonArray,
				"saleWayArray":saleWayArray,
				"modelArray":modelArray,
				"seriesArray":seriesArray,
				"productShopArray":productShopArray,
				"spreadWay":spreadWay,
				"supplierId":supplier,
				"isOld":isOld,
				"applyType":applyType,
				"checkFlag":checkFlag,
				"spu":spu,
				"showRecycle":showRecycle,
				"paramCnt":paramCnt,
				'nodeId':nodeId,
				'operationStatus':operationStatus,
				'deleFlag':deleFlag
		};
	};
	
	/**
	 * @param object { type: "show"|"hide" ,data: ["center","brand","sex","year","season","maintype","saleWay","model","series","shop","spreadWay","supplier","paramModel"] } 移除传入数组中对应组件
	 * @param array  ["center","brand","sex","year","season","maintype","saleWay","model","series","shop","spreadWay","supplier","paramModel"]  移除传入数组中对应组件
	 * @param String "param"  返回 参数json
	 * @param function 启动查询 按钮点击 并绑定传入方法
	 */
	$.fn.paramFilter=function(action,param){
		var actionType=$.type(action);
		
		switch(actionType){
		case "object" :
			var showType=action["type"];
			var showArray=action["data"];
			var btnDivId=action["btnDivId"];
			var isShow=showType=="show"?true:false;
			
			$(".param-search-btn",$(this)).hide();
			var classShowArray=[];
			//临时 处理 鞋类皮具选择条件
			classShowArray.push({clsName:'maintype-popover', show: $.inArray("maintype",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:'center-popover', show:$.inArray("center",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:'brand-popover', show:$.inArray("brand",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:'sex-popover', show: $.inArray("sex",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:'year-popover', show: $.inArray("year",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:'season-popover', show: $.inArray("season",showArray)>-1?isShow:!isShow });
			
			var advanceCnt=0;
			var showBean={clsName:"saleWayList", show: $.inArray("saleWay",showArray)>-1?isShow:!isShow };
			classShowArray.push(showBean);
			showBean["show"]?advanceCnt+=0:advanceCnt++;
			
			showBean={clsName:"modelList", show: $.inArray("model",showArray)>-1?isShow:!isShow };
			classShowArray.push(showBean);
			showBean["show"]?advanceCnt+=0:advanceCnt++;
			
			showBean={clsName:"seriesList", show: $.inArray("series",showArray)>-1?isShow:!isShow };
			classShowArray.push(showBean);
			showBean["show"]?advanceCnt+=0:advanceCnt++;
			
			showBean={clsName:"shopList", show: $.inArray("shop",showArray)>-1?isShow:!isShow };
			classShowArray.push(showBean);
			showBean["show"]?advanceCnt+=0:advanceCnt++;
			
			if(advanceCnt==4){//“更多”按钮
				classShowArray.push({clsName:"show-advance-param", show: true });
			}
			
			/*classShowArray.push({clsName:"spreadWayList", show: $.inArray("spreadWay",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:"supplierList", show: $.inArray("supplier",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:"param-model-manage", show: $.inArray("paramModel",showArray)>-1?isShow:!isShow });
			
			classShowArray.push({clsName:"param-model-detail", show: $.inArray("paramModelDetail",showArray)>-1?isShow:!isShow });*/
			
			$.fn.initParamFilterHtml($(this),classShowArray,btnDivId);
			break;
		case "array" :
				$(".param-search-btn",$(this)).hide();
				var hideParam=[];
				if($.inArray("center",action)>-1){
					hideParam.push({clsName:'center-popover' ,show: false});
				}
				if($.inArray("brand",action)>-1){
					hideParam.push({clsName:'brand-popover' ,show: false});
				}
				if($.inArray("sex",action)>-1){
					hideParam.push({clsName:'sex-popover' ,show: false});
				}
				if($.inArray("year",action)>-1){
					hideParam.push({clsName:'year-popover' ,show: false});
				}
				if($.inArray("season",action)>-1){
					hideParam.push({clsName:'season-popover' ,show: false});
				}
				
				//临时 处理 鞋类皮具选择条件
				if($.inArray("maintype",action)>-1){
					hideParam.push({clsName:'maintype-popover' ,show: false});
				}
				
				var advanceCnt=0;
				if($.inArray("saleWay",action)>-1){
					hideParam.push({clsName:"saleWayList" ,show: false});
					advanceCnt++;
				}
				if($.inArray("model",action)>-1){
					hideParam.push({clsName:"modelList" ,show: false});
					advanceCnt++;
				}
				if($.inArray("series",action)>-1){
					hideParam.push({clsName:"seriesList" ,show: false});
					advanceCnt++;
				}
				if($.inArray("shop",action)>-1){
					hideParam.push({clsName:"shopList" ,show: false});
					advanceCnt++;
				}
				
				if(advanceCnt==4){//“更多”按钮
					hideParam.push({clsName:"show-advance-param" ,show: false});
				}
				
				/*if($.inArray("spreadWay",action)>-1){
					hideParam.push({clsName:"spreadWayList" ,show: false});
				}
				
				if($.inArray("supplier",action)>-1){
					hideParam.push({clsName:"supplierList" ,show: false});
				}
				
				if($.inArray("paramModel",action)>-1){
					hideParam.push({clsName:"param-model-manage" ,show: false});
				}
				
				if($.inArray("paramModelDetail",action)>-1){
					hideParam.push({clsName:"param-model-detail" ,show: false});
				}*/
				
			$.fn.initParamFilterHtml($(this),hideParam);
			break;
		
		case "function": 
			$(".param-search-btn",$(this)).on("click",action);
			$(".param-search-btn",$(this)).show();
			break;
		case "string" : 
			if(action=="param"){	
				return $.fn.getParamFilterResultJson($(this));
			}else if(action=="modelId"){
				return $(".paramModel-option:selected",$(this)).prop("title");
			}else if(action=="modelSpuArray"){
				var modelSpuArrStr=$(".paramModel-option:selected",$(this)).attr("alt")==undefined?'[]':$(".paramModel-option:selected",$(this)).attr("alt");
				modelSpuArrStr=modelSpuArrStr.replace("[","[\"");
				modelSpuArrStr=modelSpuArrStr.replace("]","\"]");
				modelSpuArrStr=modelSpuArrStr.replace(/,/g,'","');
				var modelSpuArr=JSON.parse(modelSpuArrStr);
				return  modelSpuArr;
			}else if(action=="spuFilterType"){
				return $(".paramModel-option:selected",$(this)).attr("spu-filter-type");
			}else if(action=="setParam"){
				var paramDiv=$(this);
						
				//初始化
				$(".selected-param",paramDiv).removeClass("selected-param");
				$(".param-selected-list > label",paramDiv).hide();
				$(".param-all-check",paramDiv).show();
				$(".param-selected-list > label > span",paramDiv).remove();
				$('.advance-param-list > div > span',paramDiv).show();
				/*$('.spreadWay-select',paramDiv).val(-1);
				$('.supplier-select',paramDiv).val(-1);*/
				if(param==""||param==undefined||param==-1){
					return;
				}
						
				var spanStr="";
				var spanId="";
				var paramId="";
				for(var i in param.maintypeArray){
					var bean=param.maintypeArray[i];
					
					spanId="maintype-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#maintype"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#maintype"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}
				
				for(var i in param.centerArray){
					var bean=param.centerArray[i];
					spanId="center-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#center"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#center"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}
				
				for(var i in param.brandArray){
					var bean=param.brandArray[i];
					spanId="brand-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#brand"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#brand"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}

				for(var i in param.sexArray){
					var bean=param.sexArray[i];
					spanId="sex-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#sex"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#sex"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}
				
				for(var i in param.yearArray){
					var bean=param.yearArray[i];
					spanId="year-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#year"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#year"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}
				
				for(var i in param.seasonArray){
					var bean=param.seasonArray[i];
					spanId="season-span";
					paramId=spanId+"-"+bean;
					spanStr="<span id='"+paramId+"' class='selected-param' title='"+bean+"' name='"+spanId+"' style='margin-left:10px;' >"+$("#season"+bean,paramDiv).html()+"</span>";
					$("#"+spanId,paramDiv).append(spanStr);
					$("#season"+bean,paramDiv).addClass("selected-param");
					if($("#"+spanId,paramDiv).text().length>3){
						$("#"+spanId,paramDiv).show();
					}
				}
					
					//绑定点击移除事件
				$(".param-selected-list > label > span",paramDiv).click(function(){
					var spanId=$(this).attr("name");
					var title=$(this).attr("title");
					$(".param-list .input-group span[name='"+spanId+"'][title='"+title+"']",paramDiv).toggleClass("selected-param");
					$(".param-btn-list .popover-content span[name='"+spanId+"'][title='"+title+"']",paramDiv).toggleClass("selected-param");
					
					
					$(this).remove();
					//条件联动
					$.fn.changeParamFilter(paramDiv);
					
					
					if($("#"+spanId,paramDiv).text().length<4){
						$("#"+spanId,paramDiv).hide();
					}
				});	
					
				//刷新 popOver	
				var popOption={
					html:true,
					placement:'bottom',
					animation:true,
					content : $(".maintypeBoxList",paramDiv).html()	
				};
				
				$('.maintype-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".centerBoxList",paramDiv).html();
				$('.center-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".brandBoxList",paramDiv).html();
				$('.brand-popover',paramDiv).popover(popOption);
				
				popOption.content=$(".sexBoxList",paramDiv).html();
				$('.sex-popover',paramDiv).popover(popOption);
					
				popOption.content=$(".yearBoxList",paramDiv).html();
				$('.year-popover',paramDiv).popover(popOption);
					
				popOption.content=$(".seasonBoxList",paramDiv).html();
				$('.season-popover',paramDiv).popover(popOption);
				
				/*if(param["spreadWay"]!=""){
					$('.spreadWay-select',paramDiv).val(param["spreadWay"]);
				}*/
				
				if(param["supplier"]!=""){
					$('.supplier-select',paramDiv).val(param["supplier"]);
				}
					
				$(".advance-param-list",paramDiv).slideDown("fast");
				//切换筛选模板 直接展开  二级 选择条件
				$(".show-advance-param",paramDiv).html("收起&nbsp;<i class='icon-double-angle-up'/>");
					//条件联动
				$.fn.changeParamFilter(paramDiv);
				
				for(var i in param.saleWayArray){
					var bean=param.saleWayArray[i];
					$('#saleWay'+bean,paramDiv).addClass("selected-param");
				}
					
				for(var i in param.modelArray){
					var bean=param.modelArray[i];
					$('#model'+bean,paramDiv).addClass("selected-param");
				}
						
				for(var i in param.seriesArray){
					var bean=param.seriesArray[i];
					$('#series'+bean,paramDiv).addClass("selected-param");
				}
						
				for(var i in param.productShopArray){
					var bean=param.productShopArray[i];
					$('#shop'+bean,paramDiv).addClass("selected-param");
				}
					
			}
			break;
		default : 	$(".param-search-btn",$(this)).hide();
		
					//临时 处理 鞋类皮具选择条件
					$.fn.initParamFilterHtml($(this));
					
		} //switch
	};


})(jQuery);