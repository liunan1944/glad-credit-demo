羽山数据接入demo
====
一、[羽山数据公司](https://www.yushanshuju.com/ords/f?p=112:1::::::)
_______
    2018年11月，格兰德承接原万达征信的数据服务板块业务，并于2019年1月正式成立羽山数据。公司以万达征信为基点，以格兰德征信
    为依托,以羽山数据为主体，是提供企业数据报告、数据服务、身份认证、人脸比对服务的数据供应商。基于大数据、区块链、认知计算
    等技术，旨在打造具有创新商业模式和先进科技能力的科技企业。

二、功能实现
_______
   基于权威部门合规授权的身份认证服务，提供实现实时人脸识别认证、身份核查、移动运营商认证等服务，确保身份准确无误。
   
   
三、接入方式
_______
1.客户将调用网关机器的公网IP在调试前3天发送至羽山数据<br>
2.相关接口使用AES128加密，请先联系羽山数据获取对应的AES128的密钥（一般按HEX方式编码提供）<br>
3.构造自己的请求参数对象<br>
4.将参数对象转成JSON格式<br>
5.将JSON使用AES128加密（格兰德提供了对应的方法）<br>
6.对加密结果使用BASE64编码<br>
7.访问格兰德的请求，获取结果<br>
8.将结果进行BASE64解码<br>
9.将解码的数据进行AES128解密<br>
10.最后的结果就是需要的结果<br>

四、联系我们
_______
技术 顾先生 
