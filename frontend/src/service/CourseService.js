import axios from 'axios';

const BASE_URL = "http://localhost:8080/v1/courses";

class CourseService {
    getCourses(){
        return axios.get(BASE_URL);
    }

    createCourse(course){
        return axios.post(BASE_URL, course);
    }

    getCourseById(courseId){
        return axios.get(BASE_URL + '/' + courseId);
    }

    updateCourse(course, courseId){
        return axios.put(BASE_URL + '/' + courseId, course);
    }

    deleteCourse(courseId){
        return axios.delete(BASE_URL + '/' + courseId);
    }

    enrollStudentInCourse(courseId, studentId){
        return axios.post(BASE_URL + '/' + courseId + '/students' + '/' + studentId);
    }
}

export default new CourseService()