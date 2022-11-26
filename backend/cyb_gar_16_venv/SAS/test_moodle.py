import moodle_api
moodle_api.URL = "http://83.221.202.194:8100/"
moodle_api.KEY = "38ffa72488ce223bbc279fd4606b4940"
courses = moodle_api.CourseList()
# print(courses)


# course5 = moodle_api.call('core_course_get_contents', courseid=1)
# course5[0].keys()
# dict_keys(['id', 'summary', 'name', 'visible', 'summaryformat', 'modules'])