羽山数据接入demo
====
1.客户将调用网关机器的公网IP在调试前3天发送至羽山数据
2.相关接口使用AES128加密，请先联系羽山数据获取对应的AES128的密钥（一般按HEX方式编码提供）
3.构造自己的请求参数对象
4.将参数对象转成JSON格式
5.将JSON使用AES128加密（格兰德提供了对应的方法）
6.对加密结果使用BASE64编码
7.访问格兰德的请求，获取结果
8.将结果进行BASE64解码
9.将解码的数据进行AES128解密
10.最后的结果就是需要的结果
