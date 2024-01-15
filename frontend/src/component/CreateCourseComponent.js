import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CourseService from '../service/CourseService';

const CreateCourseComponent = (props) => {
    const navigate = useNavigate();

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

    const [message, setMessage] = useState(null);

    useEffect(() => {
    }, []);

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

    const saveCourse = (e) => {
        e.preventDefault();

        if (validateForm()) {
            CourseService.createCourse(course).then(() => {
                setMessage('The course has been created successfully!');
                setTimeout(() => {
                    setMessage(null);
                    navigate('/');
                }, 2000);
            })
            .catch((error) => {
                console.error('Error creating the course:', error);
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
            <br></br>
            <div className="container">
                <div className="row">
                    <div className="card col-md-6 offset-md-3 offset-md-3">
                        <h3 className="text-center">Create Course</h3>
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
                                <button className="btn btn-success" onClick={saveCourse}>Save</button>
                                <button className="btn btn-danger" onClick={cancel} style={{ marginLeft: "10px" }}>Cancel</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CreateCourseComponent;
