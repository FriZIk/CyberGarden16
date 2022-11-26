from .models import *
from .serializers import *
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import generics, status
from rest_framework.permissions import IsAuthenticated





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
        quizSerialized = QuizSerializer(data = request.data)
        if quizSerialized.is_valid(raise_exception=True):
            
            quizSerialized.save()
            return Response(status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)