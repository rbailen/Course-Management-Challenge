import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import CourseService from '../service/CourseService';

const GetCourseComponent = (props) => {
    const navigate = useNavigate();

    const { id } = useParams();

    const [error, setError] = useState(null);
    const [statusCode, setStatusCode] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const [course, setCourse] = useState({});

    useEffect(() => {
        const getCourseById = async () => {
            try {
                const response = await CourseService.getCourseById(id);
                const course = response.data;

                setCourse({
                    id: course.id,
                    name: course.name,
                    description: course.description,
                    schedule: course.schedule,
                    students: course.students
                });
            } catch (error) {
                setError(error.message || 'Error getting the course course');
                setStatusCode(error.response ? error.response.status : null);
                setErrorMessage(error.response ? error.response.data.detail : null);
            }
        };

        getCourseById();
    }, [id]);

    const goBack = () => {
        navigate(-1);
    };

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
                <div className="card">
                    <div className="card-body">
                        <div className="row">
                            <div className="col-lg-12 col-md-12 col-sm-12">
                                <h3 className="box-title mt-5">Get Course Details</h3>
                                <div className="table-responsive">
                                    <table className="table table-striped table-product">
                                        <tbody>
                                            <tr>
                                                <td>ID</td>
                                                <td>{course.id}</td>
                                            </tr>
                                            <tr>
                                                <td>Name</td>
                                                <td>{course.name}</td>
                                            </tr>
                                            <tr>
                                                <td>Description</td>
                                                <td>{course.description}</td>
                                            </tr>
                                            <tr>
                                                <td>Schedule</td>
                                                <td>{course.schedule}</td>
                                            </tr>
                                            <tr>
                                                <td>Students</td>
                                                <td>
                                                    {course.students && course.students.length > 0 ? (
                                                    <ul className="list-group">
                                                        {course.students.map((student) => (
                                                        <li key={student.id} className="list-group-item">
                                                            {`ID: ${student.id}, Name: ${student.name}`}
                                                        </li>
                                                        ))}
                                                    </ul>
                                                    ) : (
                                                    <p>No students enrolled</p>
                                                    )}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br></br>
                <button style={{ marginLeft: "10px" }} onClick={goBack} className="btn btn-secondary">Go Back</button>
            </div>
        </div>
    );
};

export default GetCourseComponent;
