import axios from 'axios';
import CourseService from './CourseService';

jest.mock('axios');

describe('CourseService', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('should get courses', async () => {
    const mockCourses = [{ id: 1, name: 'Course 1', description: 'Description Course 1', schedule: 'Schedule Course 1' }];
    axios.get.mockResolvedValueOnce({ data: mockCourses });

    const courses = await CourseService.getCourses();

    expect(courses).toEqual({ data: mockCourses });
    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/v1/courses');
  });

  it('should create a course', async () => {
    const mockCourse = { name: 'Course 1', description: 'Description Course 1', schedule: 'Schedule Course 1' };
    axios.post.mockResolvedValueOnce({ data: 'Course created successfully' });

    const result = await CourseService.createCourse(mockCourse);

    expect(result).toEqual({ data: 'Course created successfully' });
    expect(axios.post).toHaveBeenCalledWith('http://localhost:8080/v1/courses', mockCourse);
  });

  it('should get a course by ID', async () => {
    const courseId = 1;
    axios.get.mockResolvedValueOnce({ data: { id: courseId, name: 'Course 1', description: 'Description Course 1', schedule: 'Schedule Course 1' } });

    const result = await CourseService.getCourseById(courseId);

    expect(result).toEqual({ data: { id: courseId, name: 'Course 1', description: 'Description Course 1', schedule: 'Schedule Course 1' } });
    expect(axios.get).toHaveBeenCalledWith(`http://localhost:8080/v1/courses/${courseId}`);
  });

  it('should update a course', async () => {
    const courseId = 1;
    const updatedCourse = { name: 'Course 1', description: 'Description Updated', schedule: 'Schedule Course 1' };
    axios.put.mockResolvedValueOnce({ data: 'Course updated successfully' });

    const result = await CourseService.updateCourse(updatedCourse, courseId);

    expect(result).toEqual({ data: 'Course updated successfully' });
    expect(axios.put).toHaveBeenCalledWith(`http://localhost:8080/v1/courses/${courseId}`, updatedCourse);
  });

  it('should delete a course', async () => {
    const courseId = 1;
    axios.delete.mockResolvedValueOnce({ data: 'Course deleted successfully' });

    const result = await CourseService.deleteCourse(courseId);

    expect(result).toEqual({ data: 'Course deleted successfully' });
    expect(axios.delete).toHaveBeenCalledWith(`http://localhost:8080/v1/courses/${courseId}`);
  });

  it('should enroll a student in a course', async () => {
    const courseId = 1;
    const studentId = 4;
    
    axios.post.mockResolvedValueOnce({ data: 'Enrollment successful' });
  
    const result = await CourseService.enrollStudentInCourse(courseId, studentId);
  
    expect(result).toEqual({ data: 'Enrollment successful' });
    expect(axios.post).toHaveBeenCalledWith(
      `http://localhost:8080/v1/courses/${courseId}/students/${studentId}`
    );
  });
  
});