import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import ListCoursesComponent from './component/ListCoursesComponent';
import CreateCourseComponent from './component/CreateCourseComponent';
import GetCourseComponent from './component/GetCourseComponent';
import HeaderComponent from './component/HeaderComponent';
import FooterComponent from './component/FooterComponent';
import UpdateCourseComponent from './component/UpdateCourseComponent';
import EnrollStudentCourseComponent from './component/EnrollStudentCourseComponent';

function App() {
  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className= "container">
          <Routes>
            <Route path = "/" element={<ListCoursesComponent />} />
            <Route path = "/add-course" element={<CreateCourseComponent />} />
            <Route path = "/view-course/:id" element={<GetCourseComponent />} />
            <Route path = "/edit-course/:id" element={<UpdateCourseComponent />} />
            <Route path = "/enroll-student-course" element={<EnrollStudentCourseComponent />} />
          </Routes>
        </div>
        <FooterComponent />
        </Router>
    </div>
  );
}

export default App;
