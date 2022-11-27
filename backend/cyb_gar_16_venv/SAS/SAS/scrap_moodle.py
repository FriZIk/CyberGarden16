# Получение всех курсов вместе с картинками
import requests
import json
import re

wstoken = 'fb9d95f7040800a50a58b9f06a0296f3'
wsfunction = 'core_course_get_courses_by_field'

def get_courses_info():
    url = f'http://83.221.202.194:8100/webservice/rest/server.php?wstoken={wstoken}&wsfunction={wsfunction}&moodlewsrestformat=json'

    response = requests.get(url)

    if response.status_code != 200:
        print('no magic David Blane bro, u lose it, try again next year.')

    jsn_obj = json.loads(response.text)

    courses = []

    for course_item in jsn_obj['courses']:
        course_obj = {
            'id': course_item['id'],
            'fullname': course_item['fullname'],
            # 'shortname': course_item['shortname'],
            'description': '',
            'contacts': course_item['contacts'],
            # 'categoryid': course_item['categoryid'],
            # 'categoryname': course_item['categoryname'],
            # 'timecreated': course_item['timecreated'],
            # 'timemodified': course_item['timemodified'],
            'courselogo_url': '',
        }

        # Получение лого курса
        courselogo_url = None
        if len(course_item['overviewfiles']) > 0:
            tmp_url = course_item['overviewfiles'][0]['fileurl']
            courselogo_url = tmp_url.replace('webservice/', '', 1)

        if courselogo_url is not None:
            course_obj['courselogo_url'] = courselogo_url

        # Обработка описания, убрать все HTML теги
        course_obj['description'] = "".join(re.split("\<|\>", course_item['summary'].replace('<br />', '\n'))[::2])
        courses.append(course_obj)

    result = json.dumps(courses, ensure_ascii=False)
    return result
