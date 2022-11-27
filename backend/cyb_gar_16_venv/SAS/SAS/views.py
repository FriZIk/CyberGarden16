from .models import *
from .serializers import *
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import generics, status
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.backends import TokenBackend
from rest_framework_simplejwt.tokens import AccessToken
import requests
import json 
from datetime import date
from .scrap_moodle import *
from .serializers import CustomTokenObtainPairSerializer
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

class ListPersons(APIView):
    def get(self, request):
        query = Person.objects.all()
        serializedQuery = PersonSerializer(query, many = True)
        return Response(serializedQuery.data)


class CreatePerson(APIView):
    def post(self, request):
        person = PersonSerializer(data = request.data)
        if person.is_valid(raise_exception=True):
            finalPerson = Person.objects.create(firstname = person.data['firstname'], lastname=person.data['lastname'], middlename=person.data['middlename'], email = person.data['email'], is_lecturer = person.data['is_lecturer'])
            finalPerson.set_password(person.data['password'])
            finalPerson.save()

            return Response(status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)

class CreateQuiz(APIView):
    def post(self, request):
        print(request.body)
        theteacher = Person.objects.filter(lastname=request.data['teacher'])[0]
        quiz = Quiz.objects.create(teacher=theteacher, quiz_name = request.data['quiz_name'],questions = request.data['questions'], date = request.data['date'], groups = request.data['groups'])
        quiz.save()
        # quizSerialized = QuizSerializer(data = request.data)
        # if quizSerialized.is_valid(raise_exception=True):
            
            # quizSerialized.save()
        return Response(status=status.HTTP_201_CREATED)
        # return Response(status=status.HTTP_400_BAD_REQUEST)


class CreateSchedules(APIView):
    import requests
    group = 0
    gr_id = 0

    r = requests.get("http://165.22.28.187/schedule-api/?query=%D0%9A%D0%A2")
    # print(r.text)
    r = r.text
    grups = r.split('},')
    for i in grups:
        # print(i)
        start = i.find("КТ")
        stop = i.find("\", \"id")
        group = i[start:stop]

        start = i.find("\"group\": \"")
        stop = i.find(".htm\"")
        gr_id = i[start+10:stop]
        grupp = GroupSerializer(data = {"api_id":gr_id, "name":group})
        if grupp.is_valid():
            grupp.save()


class GetUserFromToken(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        # decodedData = TokenBackend(algorithm='HS256').decode(token,verify=True)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user is not None:
            return Response(user.lastname)
        return Response(status=status.HTTP_400_BAD_REQUEST)
        # user = decodedData["user"]


class GetSchedule(APIView):
    groupApiId = 0
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user  is not None:
            if user.scheduleId is not None:
                groupId = user.scheduleId
                
                print(groupId.api_id)
                groupApiId = groupId.api_id
            else: return Response(status=status.HTTP_400_BAD_REQUEST)
        else: return Response(status=status.HTTP_400_BAD_REQUEST)
        
        strForReq = "http://165.22.28.187/schedule-api/?group="
        strForReq = strForReq+groupApiId+".htm"
        r = requests.get(strForReq)
        r = r.text
        jsonReq = json.loads(r)
        print(jsonReq)
        # if user.is_lecturer == True:
        #     return Response(jsonReq['table']['table'])
        # else:
        today = str(date.today()).split('-')
        day = today[2]  
        for i in jsonReq['table']['table']:
            print(i)
            if ',' not in i[0]:
                continue
            if i[0].split(',')[1].split(" ")[0] == '21': 
                return Response(i)



        return Response(status=status.HTTP_400_BAD_REQUEST)


class GetMoodleInfo(APIView):
    def get(self, request):
        resJson = json.loads(get_courses_info())
        resJson = resJson[1:]
        return Response(resJson)


class StartCheckin(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user  is not None and user.is_lecturer:
            currLecture = Lecture.objects.get(id=request.data['lectureId'])
            currLecture.checkInActive = True
            currLecture.save()
            return Response(status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)

class CloseCheckin(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user  is not None and user.is_lecturer:
            currLecture = Lecture.objects.get(id=request.data['lectureId'])
            currLecture.checkInActive = False
            currLecture.save() 
            return Response(status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)

    
class StudentCheckIn(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)

        if user is not None:
            currLecture = Lecture.objects.get(id=request.data['lectureId'])
            print(currLecture)
            if currLecture.checkInActive and currLecture.checkInCode == request.data['code']:
                attendanceCheck = Attendance.objects.create(student=user, lecture=currLecture)
                # if attendanceCheck.is_valid(raise_exception=True):
                attendanceCheck.save()
                return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)

class AnswerTheQuiz(APIView):
    def post(self, request):
        token = str(request.data["token"])
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user is not None:
            quizToAnswer = Quiz.objects.get(id = request.data['quizId'])
            answeredQuiz = QuizAnswer.objects.create(quiz = quizToAnswer, answers = request.data['answer'])
            answeredQuiz.save()
            return Response(status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)

# class CreateMoodleEvent(APIView): 
    # "http://83.221.202.194:8100/webservice/rest/server.php?wstoken=fb9d95f7040800a50a58b9f06a0296f3&wsfunction=core_calendar_create_calendar_events&events[0][name]=\"{name}\"&events[0][description]=\"[{discription}]\"&events[0][format]=1&events[0][courseid]={courseid}&events[0][groupid]=0&events[0][repeats]=0&events[0][eventtype]=\"user\"&events[0][timestart]={timestart}&events[0][timeduration]=0&events[0][visible]=1&events[0][sequence]=1&moodlewsrestformat=json".format()

class CustomTokenObtainPairView(TokenObtainPairView):
    # Replace the serializer with your custom
    serializer_class = CustomTokenObtainPairSerializer

class GetAllQuiz(APIView):
    def get(self, request):
        query = Quiz.objects.all()
        quizSerialized = QuizSerializer(query, many=True)
        return Response(quizSerialized.data)

class GetMyShedules(APIView):
    def post(self, request):
        token = str(request.data["token"])
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)

        query = MySchedule.objects.get(userId_id = user.id)
        myScheduleSerialized = MySheduleSerializer(query)
        print(myScheduleSerialized.data['sched'])
        scheddJson = json.loads(myScheduleSerialized.data['sched'])
        # clean = (myScheduleSerialized.data['sched']).replace("", "")
        return Response(scheddJson)


class SetUpCode(APIView):
    def post(self, request):
        token = str(request.data["token"])
        access_token = AccessToken(token)
        userId = access_token['user_id']    
        user = Person.objects.get(id = userId)
        lectureId = request.data["lectureId"]
        lecture = Lecture.objects.get(id = lectureId)
        if user.is_lecturer :#and not lecture.checkInActive:
            lecture.checkInCode = request.data["code"]
            lecture.save()
            return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)



class AskAnonQuestion(APIView):
    def post(self, request):
        question = AnonQuestionSerializer(data = request.data)
        if question.is_valid():
            question.save()
            return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)


class GetAnonQuestions(APIView):
    def get(self, request):
        query = AnonQuestion.objects.all()
        serializedAnon = AnonQuestionSerializer(query, many=True)
        return Response(serializedAnon.data)


class SetLectureSSID(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user  is not None and user.is_lecturer:
            currLecture = Lecture.objects.get(id=request.data['lectureId'])
            currLecture.lectorSSID = request.data['SSID']
            currLecture.save()
            return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)


class ValidateSSID(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        allSSIDS = request.data["ssids"]
        if user is not None:
            currLecture = Lecture.objects.get(id=request.data['lectureId'])
            print(currLecture)
            if currLecture.checkInActive and currLecture.lectorSSID in allSSIDS:
                attendanceCheck = Attendance.objects.create(student=user, lecture=currLecture)
                # if attendanceCheck.is_valid(raise_exception=True):
                attendanceCheck.save()
                return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)


class ChangePass(APIView):
    def post(self, request):
        token = str(request.data["token"])
        print(token)
        access_token = AccessToken(token)
        userId = access_token['user_id']
        user = Person.objects.get(id = userId)
        if user is not None:
            user.set_password("test")
            user.save()
            return Response(status.HTTP_201_CREATED)
        return Response(status.HTTP_400_BAD_REQUEST)