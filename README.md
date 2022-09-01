# Klaytn 기반 NFT 거래 MVP 앱 

klaytn기반으로 NFT 아이템을 생성하고 FT 토큰을 통해 거래하는 앱입니다.


-----------------------
변동사항 : 각 repository별로 흩어져있던 서비스들을 하나의 repository에서 관리함

-------------------------
<div class="grid-image">
<img src=https://user-images.githubusercontent.com/30370933/187868417-36f51656-a1a4-4d3e-9064-77a9dc35713a.gif width="200" height="300">
<img src=https://user-images.githubusercontent.com/30370933/187868442-925b23e4-e509-4e73-bb2b-1689542e0265.gif width="200" height="300">
<img src=https://user-images.githubusercontent.com/30370933/187868449-1ab800cd-c42b-4266-8d05-aa0a92b4df8d.gif width="200" height="300">
</div>

<img src=https://user-images.githubusercontent.com/30370933/187867659-c17497c9-b90a-4c57-9d34-44dd39dc5787.png width="600" height="300">


eureka_main repository: 각 서비스들의 위치를 파악해주는 디스커버리 서비스

<img src=https://user-images.githubusercontent.com/30370933/159384551-7a7524df-87d8-40cb-a790-d66cdad78b7e.png width="600" height="300">

login-service repository: 로그인 및 인증 관리하는 서비스 
OAUTH2 인증 성공시 기존 등록된 회원인지 등록되지 않은 회원인지 파이어베이스를 통해 관리됨
로그인 성공시 jwt 토큰 발급 

ICT_blockchain repository: 블록체인 관련 서비스    
Klaytn API 호출하고, 호출 결과를 DB에 저장함

ICT_POST repository: 게시물 관련 서비스 
DB에 저장된 정보들을 클라이언트 앱의 API호출에 응답해줌 

ICT_gateway: 서비스들의 요청을 관리하는 게이트웨이 서비스
클라이언트가 하나의 포트로만 api를 호출할수 있도록 관리하고 jwt 토큰의 유효성을 판단하여 접근 권한을 관리함    

프로젝트 구성도 
https://dull-plough-973.notion.site/NFT-bb9f31bdc9fc4138b23366969956afce

-----------------------
# 팀구성
송인용: 백엔드 서비스 개발 및 배포 인프라 관리

최문석: 플러터 앱 개발

api 관리 문서
-----------------------
https://songinyong.gitbook.io/nft/



