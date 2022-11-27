"""SAS URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path
from .views import *
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
    TokenVerifyView,    
)


urlpatterns = [
    path('admin/', admin.site.urls),
    path("createperson/", CreatePerson.as_view()),
    path("listperson/", ListPersons.as_view()),
    path('token/', CustomTokenObtainPairView.as_view(), name='token_obtain_pair'),
    # path('token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('token/verify/', TokenVerifyView.as_view(), name='token_verify'),
    path('createquiz/', CreateQuiz.as_view()),
    path('createschedule/', CreateSchedules.as_view()),
    path('getuser/', GetUserFromToken.as_view()),
    path('getschedule/', GetSchedule.as_view()),
    path('getmoodleinfo/', GetMoodleInfo.as_view()),
    path('startcheckin/', StartCheckin.as_view()),
    path('closecheckin/', CloseCheckin.as_view()),
    path('studentcheckin/', StudentCheckIn.as_view()),
    path('studentcheckinwifi/', ValidateSSID.as_view()),
    path('answerquiz/', AnswerTheQuiz.as_view()),
    path('getquiz/', GetAllQuiz.as_view()),
    path('getmyschedule/', GetMyShedules.as_view()),
    path('askanonquestion/', AskAnonQuestion.as_view()),
    path('getanonquestion/', GetAnonQuestions.as_view()),
    path('setssid/', SetLectureSSID.as_view()),
    path('setcode/', SetUpCode.as_view()),
    path('changepass/', ChangePass.as_view()),


]
