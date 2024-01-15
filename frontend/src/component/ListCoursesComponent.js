import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CourseService from '../service/CourseService';

const ListCoursesComponent = () => {
  const navigate = useNavigate();

  const [courses, setCourses] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [courseToDelete, setCourseToDelete] = useState(null);
  const [error, setError] = useState(null);
  const [statusCode, setStatusCode] = useState(null);

  useEffect(() => {
    const getCourses = async () => {
      try {
        const response = await CourseService.getCourses();
        setCourses(response.data);
      } catch (error) {
        setError(error.message || 'Error getting courses');
        setStatusCode(error.response ? error.response.status : null);
      }
    };

    getCourses();

  }, []);

  const createCourse = () => {
    navigate('/add-course');
  };

  const enrollStudentInCourse = () => {
    navigate('/enroll-student-course');
  };

  const viewCourse = (id) => {
    navigate(`/view-course/${id}`);
};

  const editCourse = (id) => {
    navigate(`/edit-course/${id}`);
  };

  const showDeleteConfirmation = (course) => {
    setCourseToDelete(course);
    setShowConfirmation(true);
  };

  const hideDeleteConfirmation = () => {
    setCourseToDelete(null);
    setShowConfirmation(false);
  };

  const confirmDeleteCourse = () => {
    if (courseToDelete) {
      CourseService.deleteCourse(courseToDelete.id)
        .then(() => {
          setCourses(courses.filter(course => course.id !== courseToDelete.id));
          hideDeleteConfirmation();
        })
        .catch((error) => {
          hideDeleteConfirmation();
        });
    }
  };

  return (
    <div>
      {error && (
        <div className="alert alert-danger" role="alert">
          {`Error: ${error} (Status Code: ${statusCode || 'N/A'})`}
        </div>
      )}
    
      <h2 className="text-center">Courses</h2>
      <button className="btn btn-success" onClick={createCourse}> Create Course</button>
      <button className="btn btn-secondary" onClick={enrollStudentInCourse} style={{ marginLeft: "10px" }}> Enroll Student in Course</button>
      <br></br>
      <br></br>
      <div className="row">
        <table className="table table-striped table-bordered">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Description</th>
              <th>Schedule</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {courses.map(course => (
              <tr key={course.id}>
                <td>{course.id}</td>
                <td>{course.name}</td>
                <td>{course.description}</td>
                <td>{course.schedule}</td>
                <td>
                  <button onClick={() => editCourse(course.id)} className="btn btn-warning">Update</button>
                  <button style={{ marginLeft: '10px' }} onClick={() => showDeleteConfirmation(course)} className="btn btn-danger">Delete</button>
                  <button style={{ marginLeft: '10px' }} onClick={() => viewCourse(course.id)} className="btn btn-primary">View</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showConfirmation && (
        <div className="modal" style={{ display: 'block', backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Confirm Deletion</h5>
                <button type="button" className="btn-close" onClick={hideDeleteConfirmation}></button>
              </div>
              <div className="modal-body">Are you sure you want to delete the course?</div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={hideDeleteConfirmation}>Cancel</button>
                <button type="button" className="btn btn-danger" onClick={confirmDeleteCourse}>Confirm</button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ListCoursesComponent;