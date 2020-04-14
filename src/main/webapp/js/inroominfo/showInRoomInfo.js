layui.use(['jquery','layer','table','form','laydate'], function() {
    var $ = layui.jquery,   //jquery
        layer = layui.layer,  //弹出层
        table = layui.table,  //数据表格
        form = layui.form,  //表单
        laydate = layui.laydate;   //日期

    var currentPage = 1;

    //日期时间选择器,显示当前的退房时间
    laydate.render({
        elem: '#endDate'  //日期插件加入的容器
        ,type: 'datetime'  //日期类型
        ,format:'yyyy/MM/dd HH:mm:ss'  //显示日期的格式
        ,value:new Date()  //值为当前时间
    });

    //入住信息的方法级渲染
    table.render({
        elem: '#demo' //数据存放的容器，为table标签，其id="demo"
        ,height: 412  //容器高度
        ,url: 'inRoomInfo/loadPageByPramas' //数据接口或者访问服务器端的数据路径
        ,limit:3   //自定义每一页的数据条数
        ,limits:[2,3,5,8,10]
        ,even:true  //逐行背景色深浅不一
        ,page: true //开启分页
        ,cols: [[ //表头  field: 'id'表示从实体对象的属性中取到数据放入容器里
            {type:'checkbox'}
            ,{field: 'id', title: 'ID', align:'center', width:80, sort: true}
            ,{field: 'roomNum', title: '房间号', align:'center',width:150,templet:'<div>{{d.rooms.roomNum}}</div>'}
            ,{field: 'roomPic', title: '封面图', width:150, align:'center',sort: true,templet:'<div><img src="img/{{d.rooms.roomPic}}"/></div>'}
            ,{field: 'roomTypeName', title: '类型', width:120,align:'center',templet:'<div>{{d.rooms.roomType.roomTypeName}}</div>'}
            ,{field: 'roomPrice', title: '价格', width: 120,align:'center', sort: true,templet:'<div>{{d.rooms.roomType.roomPrice}}</div>'}
            ,{field: 'customerName', title: '客人姓名', width: 130,align:'center'}
            ,{field: 'gender', title: '性别', width: 100, align:'center',sort: true,templet:'#genderTpl'}
            ,{field: 'idcard', title: '身份证号', width: 220, align:'center',sort: true}
            ,{field: 'isVip', title: '会员', width: 100, align:'center',sort: true,templet:'#isVipTpl'}
            ,{field: 'phone', title: '手机号', width: 180, align:'center',sort: true}
            ,{field: 'money', title: '押金', width: 120,align:'center'}
            ,{field: 'createDate', title: '入住时间', width: 200,align:'center'}
            ,{field: 'outRoomStatus', title: '状态', width: 120,align:'center',templet:'#outRoomStatusTpl'}
            ,{fixed: 'right',title: '操作', width:190, align:'center', toolbar: '#barDemo'}
        ]],
        done:function (res, curr, count) {  //执行每一次分页加载时数据渲染完后的函数回调
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
         //   console.log(res);  //就是控制器所响应回的Map集合中的数据，此时数据格式为JOSN
            //得到当前页码
        //    console.log(curr);
            currentPage = curr;
            //得到数据总量
        //    console.log(count);
            hoverOpenImg();  //加载放大镜
        }
    });

    //监听工具条
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'query'){ //查看
            layer.msg("查看了id="+data.id+"的入住信息数据")
        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                //向服务端发送删除（修改入住信息状态）指令
                updstatus(obj);
                layer.close(index);
            });
        } else if(layEvent === 'exitRoom'){ //退房
            //1.弹出之前将基本的入住信息数据回显到退房操作界面
            form.val("exitInRoomInfoForm", { //formTest 即 class="layui-form" 所在元素属性 lay-filter="" 对应的值
                "inRoomInfo_id": data.id // "name": "value"
                ,"roomNum": data.rooms.roomNum
                ,"customerName": data.customerName
                ,"idcard": data.idcard
                ,"roomPrice": data.rooms.roomType.roomPrice
                ,"createDate": data.createDate
            });
            //特殊数据回显
            //a.是否会员
            if(data.isVip==1){
                $("#isVip").val("是");
                //根据身份证号查询其会员数据
                loadVipByIdCard(data.idcard);
            }else {
                $("#isVip").val("否");
            }
            //c.计算回显入住天数
            var beginDateStr = getDateStr(data.createDate);
            var endDateStr = getDateStr($("#endDate").val());
            var days = getDays(beginDateStr,endDateStr)
            if(days==0){  //当天退房算1天
                days=1;
            }
            $("#days").text(days);  //回显入住天数
            //d.计算并回显消费金额
            //zprice=days*price*vipRate+otherPrice
            //房间单价
            var price = data.rooms.roomType.roomPrice;
            //折扣
            var vipRate = $("#vipRate").val();
            //总价(不包含其他消费)
            var zprice = days*price*vipRate;  //没有包含其他消费，时房间的入住金额
            //进行总价的回显
            $("#zprice").text(zprice);
         //   var zprice = days*price*
            //清空其他消费和备注
            $("#otherPrice").val(0);
            $("#remark").val("");
            //2.弹出退房的操作界面
            layer.open({
                type:1,  //弹框类型
                title:"退房操作界面", //弹框标题
                area:['800px','620px'],  //弹框的宽高度
                anim: 4,  //弹框弹出时的动画效果
                shade:0.5,  //背景的透明度
                content:$("#exitInRoomInfoDiv")  //弹出的内容
            });
            //3.当用户输入其他金额时，总价发生改变
            $("#otherPrice").blur(function () {
                var otherPrice = parseFloat($(this).val());
                //计算最终消费金额
                var zoprice = zprice + otherPrice;
                $("#zprice").text(zoprice); //填充消费金额
            });
            //4.提交退房监听，监听form表单中的submi提交
            form.on('submit(demo3)', function(data){//submit(demo1)与按钮中的lay-filter="demo1"值保持一致
                //表单中的数据data.field
                //进行退房操作：页面发送退房操作请求
                //1.修改入住信息状态：未退房-->已退房；修改
                //2.将入住信息中的房间状态：已入住-->打扫；修改
                //3.生成此入住信息的订单数据；添加  ,以添加订单数据为主
                //注意：三个数据库表的操作均改变了数据的结构，所有要控制事物
                //此业务功能中要在业务层加上事物控制：三个Mapper底层的操作数据库要么同时成功，要么同时失败。
                //构建订单添加的参数对象
                var saveJsonOrders = {};
                var nowDate = new Date();  //js中的系统当前时间
                //设置订单添加的相关参数  key:value
                saveJsonOrders['orderNum'] = dateReplace(getNowDate(nowDate))+getRandom(6);
                saveJsonOrders['orderMoney'] = $("#zprice").text();  //订单总价
                saveJsonOrders['remark'] = data.field.remark  //订单备注
                saveJsonOrders['orderStatus'] = '0'  //订单状态
                saveJsonOrders['iriId'] = data.field.inRoomInfo_id  //入住信息id
                saveJsonOrders['createDate'] = getNowDate(nowDate);  //设置订单创建时间
                saveJsonOrders['flag'] = '1';  //设置订单是否显示
                //房间编号,客人姓名,入住时间,退房时间,入住天数
                var orderOther = data.field.roomNum+','+data.field.customerName+','+data.field.createDate+','+data.field.endDate+','+$("#days").text();
                saveJsonOrders['orderOther'] = orderOther    //退房时的客人信息时间等等
                var orderPrice = data.field.roomPrice+','+data.field.otherPrice+','+zprice
                saveJsonOrders['orderPrice'] = orderPrice   //退房时的各种金额
                console.log(saveJsonOrders);
                //异步请求方式添加订单数据
                saveOrders(saveJsonOrders);
                layer.closeAll();  //关闭所有弹框
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        }
    });

    //自定义表单验证
    form.verify({
        otherPrice: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value < 0) {
                return '其他消费金额要大于0';
            }
        }
    });


    /***********************自定义的函数****************************/
    //图片放大镜
    function hoverOpenImg(){
        var img_show = null; // tips提示
        $('td img').hover(function(){
            var img = "<img class='img_msg' src='"+$(this).attr('src')+"' style='width:230px;' />";
            img_show = layer.tips(img, this,{
                tips:[2, 'rgba(41,41,41,.5)']
                ,area: ['260px']
            });
        },function(){
            layer.close(img_show);
        });
        $('td img').attr('style','max-width:70px');
    }

    //修改入住信息显示状态
    function updstatus(obj) {
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"inRoomInfo/updByPrimaryKeySelective",   //访问服务器端的路径
            data:{"id":obj.data.id,"status":0},  //传到服务器端参数JSON格式数据
            success:function (data) {  //请求执行正常函数回调
                if(data==='success'){  //模拟数据库删除操作成功
                    layer.msg('入住信息删除成功。。', {icon: 1,time:2000,anim: 4,shade:0.5});
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                }else {
                    layer.msg('入住信息删除失败！！', {icon: 2,time:2000,anim: 3,shade:0.5});
                }
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }

    //根据身份证号查询会员卡号
    function loadVipByIdCard(idcard) {
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"vip/loadByPramas",   //访问服务器端的路径
            async:false,  //允许ajax外部的变量获得去数据
            data:{"idcard":idcard},  //传到服务器端参数JSON格式数据
            success:function (data) {  //请求执行正常函数回调
                //b.回显会员卡号
                $("#vipNum").val(data.vipNum);
                //设置会员折扣
                $("#vipRate").val(data.vipRate);
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }

    //添加订单
    function saveOrders(saveJsonOrders) {
        $.ajax({
            type:"POST",  //请求方式，POST请求
            url:"orders/save",   //访问服务器端的路径
            data:saveJsonOrders,  //传到服务器端参数JSON格式数据
            success:function (data) {  //请求执行正常函数回调
                if(data=="success"){
                    layer.msg('退房成功。。', {icon: 1,time:2000,anim: 4,shade:0.5});
                    //刷新数据表格，重新发送异步请求访问服务器端获取查询数据
                    table.reload('demo', {  //指明具体要重新加载的table容器，容器id
                        page: {
                            curr: currentPage//重新从当前页开始
                        }
                    });
                }else {
                    layer.msg('退房失败！！', {icon: 2,time:2000,anim: 3,shade:0.5});
                }
            },
            error:function () {  //请求执行异常时的函数回调
                layer.msg('服务器异常', {icon: 3,time:2000,anim: 6,shade:0.5});
            }
        });
    }

    //将目前的时间格式2019/08/06 12:12:08  -->  2019/08/06
    function getDateStr(dateStr) {
        var indexOf = dateStr.indexOf(" ");  //取到" "的下标
        dateStr = dateStr.substring(0,indexOf);  //第1个参数为下标，第2个参数为切割的字符串长度
        return dateStr;
    }

    //计算天数
    function getDays(startDate,endDate){  //2019/09/09   2019/10/10
        var date1Str = startDate.split("/");
        var date1Obj = new Date(date1Str[0],(date1Str[1]-1),date1Str[2]);
        var date2Str = endDate.split("/");
        var date2Obj = new Date(date2Str[0],(date2Str[1]-1),date2Str[2]);
        var t1 = date1Obj.getTime();
        var t2 = date2Obj.getTime();
        var datetime=1000*60*60*24;
        var minusDays = Math.floor(((t2-t1)/datetime));
        var days = Math.abs(minusDays);
        return minusDays;
    }

    //获取当前时间字符串     Date()   ---->  yyyy/MM/dd HH:mm:ss 格式的字符串
    function getNowDate(date) {
        var sign1 = "/";
        var sign2 = ":";
        var year = date.getFullYear() // 年
        var month = date.getMonth() + 1; // 月
        var day  = date.getDate(); // 日
        var hour = date.getHours(); // 时
        var minutes = date.getMinutes(); // 分
        var seconds = date.getSeconds() //秒
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (day >= 0 && day <= 9) {
            day = "0" + day;
        }
        if (hour >= 0 && hour <= 9) {
            hour = "0" + hour;
        }
        if (minutes >= 0 && minutes <= 9) {
            minutes = "0" + minutes;
        }
        if (seconds >= 0 && seconds <= 9) {
            seconds = "0" + seconds;
        }
        var currentdate = year + sign1 + month + sign1 + day + " " + hour + sign2 + minutes + sign2 + seconds ;
        return currentdate;
    }

    //把 2019/01/01 12:12:12  -->  20190101121212
    function dateReplace(date) {
        date = date.replace("/","");
        date = date.replace("/","");
        date = date.replace(" ","");
        date = date.replace(":","");
        date = date.replace(":","");
        return date;
    }

    //获取随机数
    function getRandom(num) {
        var count = '';   //随机数
        for (var i=0;i<num;i++){
            count += parseInt(Math.random()*10)  //0.123123123...
        }
        return count;
    }

});