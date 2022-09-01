# eureka_main
-----------------------
변동사항 : 각 repository별로 흩어져있던 서비스들을 하나의 repository에서 관리함
eureka-main repository
-------------------------



NFT 거래 시스템에서 Service Discovery를 담당하는 서비스 입니다.
eureka을 이용하여 구현하였습니다.

![유레카 작동 사진](https://user-images.githubusercontent.com/30370933/159384551-7a7524df-87d8-40cb-a790-d66cdad78b7e.png)



login-service repository: 로그인 및 인증 관리하는 서비스   
ICT_blockchain repository: 블록체인 관련 서비스    
ICT_POST repository: 게시물 관련 서비스   
ICT_gateway: 서비스들의 요청을 관리하는 게이트웨이 서비스로 클라이언트가   
  api호출을 편리하게 하고 필터링으로 토큰의 유효성등을 판단하여 서비스의 접근을 관리하는 서비스    


프로젝트 전체 구성도 
https://dull-plough-973.notion.site/NFT-bb9f31bdc9fc4138b23366969956afce
