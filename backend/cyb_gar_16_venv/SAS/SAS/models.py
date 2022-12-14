from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
from django.conf import settings
from django.urls import reverse
import jwt

from datetime import datetime, timedelta


class PersonManager(BaseUserManager):
    def create_user(self, firstname, lastname, middlename, email, password=None):
        """ Создает и возвращает пользователя с имэйлом, паролем и именем. """
        if username is None:
            raise TypeError('Users must have a username.')

        if email is None:
            raise TypeError('Users must have an email address.')

        user = self.model(username=username, email=self.normalize_email(email))
        user.set_password(password)
        user.save()

        return user

    def create_superuser(self, firstname, lastname, middlename, email, password=None):
        """ Создает и возввращет пользователя с привилегиями суперадмина. """
        if password is None:
            raise TypeError('Superusers must have a password.')

        user = self.create_user(username, email, password)
        user.is_superuser = True
        user.is_staff = True
        user.save()

        return user

class Person(AbstractBaseUser):
    firstname   = models.CharField(max_length=100)
    lastname    = models.CharField(max_length=100)
    middlename  = models.CharField(max_length=100)
    password    = models.CharField(max_length=120)
    email       = models.EmailField(max_length = 100, unique=True)
    is_lecturer = models.BooleanField(default= False)
    scheduleId  = models.ForeignKey('Group', on_delete=models.CASCADE )

    USERNAME_FIELD = 'email'
    
    objects = PersonManager()

    def __str__(self):
        return self.firstname


class Quiz(models.Model):
    quiz_name   = models.CharField(max_length=100)
    questions   = models.CharField(max_length=1024)
    teacher     = models.ForeignKey('Person', on_delete=models.CASCADE)
    date        = models.DateTimeField(null=True, default=None)
    groups      = models.CharField(max_length=100)

    
class QuizAnswer(models.Model):
    quiz    = models.ForeignKey('Quiz', on_delete=models.CASCADE)
    answers = models.CharField(max_length=1024)

class Group(models.Model):
    api_id = models.CharField(max_length=16, unique=True)
    name = models.CharField(max_length=16)


class Lecture(models.Model):
    lector      = models.ForeignKey('Person', on_delete=models.CASCADE)
    checkInCode = models.CharField(max_length=8)
    checkInActive   = models.BooleanField(default=False)
    dateAndTime = models.DateTimeField()
    lectorSSID  = models.CharField(max_length=128, null=True, default=None)
    test        = models.ManyToManyField('Quiz')


class AnonQuestion(models.Model):
    question    = models.CharField(max_length=256)
    datetime    = models.DateTimeField()
    leccture    = models.CharField(max_length=16, null=True, default=None)

class Attendance(models.Model):
    # checkIn = models.BooleanField(default=False)
    student = models.ForeignKey('Person', on_delete=models.CASCADE)
    lecture = models.ForeignKey('Lecture', on_delete=models.CASCADE)
    test    = models.ManyToManyField('Quiz', null=True)
    class Meta:
        unique_together = ('student', 'lecture')

class MySchedule(models.Model):
    userId  = models.ForeignKey('Person', on_delete=models.CASCADE)
    sched   = models.CharField(max_length=1024)