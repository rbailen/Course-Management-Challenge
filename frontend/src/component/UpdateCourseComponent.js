import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CourseService from '../service/CourseService';

const UpdateCourseComponent = (props) => {
    const navigate = useNavigate();

    const { id } = useParams();

    const [error, setError] = useState(null);
    const [statusCode, setStatusCode] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const [course, setCourse] = useState({
        name: '',
        description: '',
        schedule: ''
    });

    const [errors, setErrors] = useState({
        name: '',
        description: '',
        schedule: ''
    });

    useEffect(() => {
        const getCourseById = async () => {
        try {
            const response = await CourseService.getCourseById(id);
            const course = response.data;
            setCourse({
                name: course.name,
                description: course.description,
                schedule: course.schedule
            });
        } catch (error) {
            setError(error.message || 'Error getting the course course');
            setStatusCode(error.response ? error.response.status : null);
            setErrorMessage(error.response ? error.response.data.detail : null);
        }
        };

        getCourseById();

    }, [id]);

    const validateForm = () => {
        let isValid = true;
        const newErrors = { name: '', description: '', schedule: '' };

        if (!course.name.trim()) {
            isValid = false;
            newErrors.name = 'Name is required';
        }

        if (!course.description.trim()) {
            isValid = false;
            newErrors.description = 'Description is required';
        }

        if (!course.schedule.trim()) {
            isValid = false;
            newErrors.schedule = 'Schedule is required';
        }

        setErrors(newErrors);

        return isValid;
    }

    const updateCourse = (e) => {
        e.preventDefault();

        if (validateForm()) {
            CourseService.updateCourse(course, id).then(() => {
                navigate('/');
            })
            .catch((error) => {
                console.error('Error updating the course:', error);
            });
        }
    }

    const handleChange = (event) => {
        const { name, value } = event.target;
        setCourse({ ...course, [name]: value });
    }

    const cancel = () => {
        navigate('/');
    }

    return (
        <div>
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
                        <h3 className="text-center">Update Course</h3>
                        <div className="card-body">
                            <form>
                                <div className="form-group">
                                    <label>Name:</label>
                                    <input placeholder="Name" name="name" className="form-control"
                                        value={course.name} onChange={handleChange} />
                                    {errors.name && <div className="text-danger">{errors.name}</div>}
                                </div>
                                <br></br>
                                <div className="form-group">
                                    <label>Description:</label>
                                    <input placeholder="Description" name="description" className="form-control"
                                        value={course.description} onChange={handleChange} />
                                    {errors.description && <div className="text-danger">{errors.description}</div>}
                                </div>
                                <br></br>
                                <div className="form-group">
                                    <label>Schedule:</label>
                                    <input placeholder="Schedule" name="schedule" className="form-control"
                                        value={course.schedule} onChange={handleChange} />
                                    {errors.schedule && <div className="text-danger">{errors.schedule}</div>}
                                </div>
                                <br></br>
                                <button className="btn btn-success" onClick={updateCourse}>Save</button>
                                <button className="btn btn-danger" onClick={cancel} style={{ marginLeft: "10px" }}>Cancel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default UpdateCourseComponent;