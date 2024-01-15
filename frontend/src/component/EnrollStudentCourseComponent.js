import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CourseService from '../service/CourseService';

const EnrollStudentCourseComponent = (props) => {
    const navigate = useNavigate();

    const [error, setError] = useState(null);
    const [statusCode, setStatusCode] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const [course, setCourse] = useState({
        studentId: '',
        courseId: ''
    });

    const [errors, setErrors] = useState({
        studentId: '',
        courseId: ''
    });

    const [message, setMessage] = useState(null);

    useEffect(() => {
    }, []);

    const validateForm = () => {
        let isValid = true;
        const newErrors = { studentId: '', courseId: '' };

        if (!course.studentId.trim()) {
            isValid = false;
            newErrors.studentId = 'Student ID is required';
        }

        if (!course.courseId.trim()) {
            isValid = false;
            newErrors.courseId = 'Course ID is required';
        }

        setErrors(newErrors);

        return isValid;
    }

    const enrollStudentInCourse = (e) => {
        e.preventDefault();

        if (validateForm()) {
            setError(null);
            setStatusCode(null);
            setErrorMessage(null);

            CourseService.enrollStudentInCourse(course.courseId, course.studentId).then(() => {
                setMessage('The student has been successfully enrolled in the course!');
                setTimeout(() => {
                    setMessage(null);
                    navigate('/');
                }, 2000);
            })
            .catch((error) => {
                setError(error.message || 'Error enrolling student in course');
                setStatusCode(error.response ? error.response.status : null);
                setErrorMessage(error.response ? error.response.data.detail : null);
            });
        }
    }

    const handleChange = (event) => {
        const { name, value } = event.target;
        setCourse({ ...course, [name]: value });
        setErrors({ ...errors, [name]: '' });
    }

    const cancel = () => {
        navigate('/');
    }

    return (
        <div>
            {message && <div className="alert alert-success">{message}</div>}
            {error && (
                <div className="alert alert-danger" role="alert">
                {`Error: ${error}`}
                <br></br>
                {`Status Code: ${statusCode || 'N/A'}`}
                <br></br>
                {`Message: ${errorMessage || 'N/A'}`}
                </div>
            )}
            <br></br>
            <div className="container">
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <h3 className="text-center">Enroll Student in Course</h3>
                        <div className="card-body">
                            <form>
                                <div className="form-group">
                                    <label>Student ID:</label>
                                    <input placeholder="Student ID" name="studentId" className="form-control"
                                        value={course.studentId} onChange={handleChange} />
                                    {errors.studentId && <div className="text-danger">{errors.studentId}</div>}
                                </div>
                                <br></br>
                                <div className="form-group">
                                    <label>Course ID:</label>
                                    <input placeholder="Course ID" name="courseId" className="form-control"
                                        value={course.courseId} onChange={handleChange} />
                                    {errors.courseId && <div className="text-danger">{errors.courseId}</div>}
                                </div>
                                <br></br>
                                <button className="btn btn-success" onClick={enrollStudentInCourse}>Save</button>
                                <button className="btn btn-danger" onClick={cancel} style={{ marginLeft: "10px" }}>Cancel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default EnrollStudentCourseComponent;