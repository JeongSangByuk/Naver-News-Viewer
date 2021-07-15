# 😄 Naver-News-Viewer
<img src = "https://img.shields.io/badge/ProjectType-SingleToyProject-orange?style=flat-square">  <img src = "https://img.shields.io/badge/Tools-AndroidStudio-brightgreen?style=flat-square&logo=AndroidStudio">  <img src = "https://img.shields.io/badge/Language-Java-critical?style=flat-square&logo=Java">
> MVP 패턴을 학습하기 위해, </br>
> MVP 패턴을 적용해서 개발한 네이버 최신 속보 뷰어 안드로이드 어플리케이션 입니다.
<p align="center"> <img src = "https://user-images.githubusercontent.com/64072741/125599121-30f40923-44a8-472c-8c2a-de1ec6fb85c8.png" > </p>

##  📝  Features
+ 네이버에서 제공하는 5가지 카테고리(정치, 경제, 사회, 생활, 과학)의 최신 속보를 실시간으로 확인할 수 있습니다.
+ 관심있는 뉴스를 아카이빙하여, 나만의 뉴스 아카이브 목록을 만들 수 있습니다.
+ 뉴스 리스트에서 뉴스를 클릭하면 바로 뉴스 사이트로 연결됩니다.
</br>

##  📚  Stack & Library
+ MVP 패턴 : View와 Model을 완전히 분리시키고 둘 사이의 상호작용을 Presenter에 위임하는 MVP 패턴 사용.
+ Singleton 패턴 : 데이터베이스 객체의 유일성을 보장하기 위해서 Singleton 패턴 사용.
+ Data Binding : 효율적으로 데이터를 UI 요소에 연결하기 위해 Data Binding 사용.
+ RxJava : 비동기 통신을 위해 사용.
+ Glide : 효과적인 이미지로딩을 위해 사용.
+ Lottie Animation : 고품질 애니메이션 효과를 위해 사용.
+ Jsoup : Html Parsing을 위해 사용.
+ Room : 로컬 데이터베이스를 활용하기 위해 사용.
</br>

##  🖥️  Preview
<p align="center"> <img src = "https://user-images.githubusercontent.com/64072741/125657755-fa90b789-a5d0-4b10-8868-b51d17f7fc8c.gif" width = "40%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src = "https://user-images.githubusercontent.com/64072741/125659291-f1ec4605-5e8a-405c-a363-ce7221823255.gif" width = "40%"></p>
</br></br>

##  🛠️  Architecture
<img src = "https://user-images.githubusercontent.com/64072741/125657353-4fed9ff2-08f3-445d-9a54-d4873a1b874b.png"> 
<br>

+ 네이버 최신 속보 뷰어 어플리케이션에는 MVP 패턴을 기반으로 설계했습니다.<br>
  MVP 패턴은 Modle-View-Presenter로 구성되며, View와 Model을 완전하게 분리해서 사용하기 위해 적용합니다.
+ Presenter은 View와 인터페이스로 연결되며 View와 Model 사이의 데이터를 전달하고 View를 업데이트합니다.
+ Google의 Architecture 정의를 따라, Contract 인터페이스를 정의한 후 View와 Presenter에 implement해서 사용했습니다.<br>
  이러한 방식은 View와 Presenter 간의 상호작용에 대한 설계를 도와주며 개발자가 직관적으로 클래스 기능을 파악할 수 있게끔 합니다.

