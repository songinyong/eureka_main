# NFT 거래 앱 제작
-----------------------
변동사항 : 각 repository별로 흩어져있던 서비스들을 하나의 repository에서 관리함
eureka-main repository
-------------------------

![image33](https://user-images.githubusercontent.com/30370933/187868417-36f51656-a1a4-4d3e-9064-77a9dc35713a.gif){: width="150" height="200"}
![image34](https://user-images.githubusercontent.com/30370933/187868442-925b23e4-e509-4e73-bb2b-1689542e0265.gif){: width="150" height="200"}
![image32](https://user-images.githubusercontent.com/30370933/187868449-1ab800cd-c42b-4266-8d05-aa0a92b4df8d.gif){: width="150" height="200"}

![nft4](https://user-images.githubusercontent.com/30370933/187867659-c17497c9-b90a-4c57-9d34-44dd39dc5787.png)


NFT 거래 시스템에서 Service Discovery를 담당하는 서비스 입니다.
eureka을 이용하여 구현하였습니다.


eureka_main
![유레카 작동 사진](https://user-images.githubusercontent.com/30370933/159384551-7a7524df-87d8-40cb-a790-d66cdad78b7e.png)



login-service repository: 로그인 및 인증 관리하는 서비스   
ICT_blockchain repository: 블록체인 관련 서비스    
ICT_POST repository: 게시물 관련 서비스   
ICT_gateway: 서비스들의 요청을 관리하는 게이트웨이 서비스로 클라이언트가   
  api호출을 편리하게 하고 필터링으로 토큰의 유효성등을 판단하여 서비스의 접근을 관리하는 서비스    


프로젝트 전체 구성도 
https://dull-plough-973.notion.site/NFT-bb9f31bdc9fc4138b23366969956afce
