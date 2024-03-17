import { useState, useEffect, useContext } from "react";
import { Alert, Button, Modal } from "react-bootstrap";
import { unenrollCourses } from "../API/StudentAPI";
import { getEnrolledCourses } from "../API/CourseAPI";
import CourseTable from "../Component/CourseTable";
import { ClientContext} from "../Router/ClientRouter";
import { RegisteredCourses_AlertModal, RegisteredCourses_ResultModal } from "../Component/Modal";
const RegisteredCoursesPage=()=>{
          const {courseData, setError}=useContext(ClientContext);
          const [courseIds, setCourseIds]=useState(new Set());
          const [regCourseIds, setRegCourseIds]=useState([]);
          const [modal, setModal]=useState({show:0, data:null});//0:ko show, 1:show alertModal, 2:show resultModal
          const [isDelButtonDisabled, setIsDelButtonDisabled]=useState(true);
          const sendRequest=()=>{
                    unenrollCourses([...courseIds]).then(res=>{
                              const result=new Map(Object.entries(res.data));
                              setRegCourseIds(prev=>{
                                    const updatedRegCourseIds=prev.filter(courseId=>result.get(courseId)&&result.get(courseId)=="Unenroll successfully");
                                    return updatedRegCourseIds;
                              })
                              setModal({show:2, data:regCourseIds});
                      })
                      .catch(err=>setError(err))
           }
            const handleClose=()=>{
                  setModal({show:false, data:[]});
            }
            useEffect(()=>{
                        if(courseIds.size===0) setIsDelButtonDisabled(true);
                        else setIsDelButtonDisabled(false);
            },[courseIds])
            useEffect(()=>{
                        getEnrolledCourses().then(res=>{
                              setRegCourseIds(res.data);
                        })
                  },[])
                  const filterCourses=courseData.filter(course=>regCourseIds.indexOf(course.id)>-1);
                  const regInfo={numberOfCourses:filterCourses.length,creditNumber:0};
                  for(let course of filterCourses) regInfo.creditNumber+=course.subject.theoryCreditNumber;
        return(
          <>
                    <div className="main-page">
                              <div className="ContentAlignment">
                                        <h4>Đã đăng kí: {regInfo.numberOfCourses} lớp({regInfo.creditNumber} tín chỉ)</h4>
                                        <button type="button" className="btn btn-danger" onClick={()=>setModal({show:1,data:null})} disabled={isDelButtonDisabled}>Hủy đăng kí</button>
                                        
                              </div>
                              <CourseTable filterCourses={filterCourses} courseIds={courseIds} setCourseIds={setCourseIds}/>
                    </div>
                    {modal.show!=0&&
                          (modal.show==1?<RegisteredCourses_AlertModal modal={modal} courseData={courseData} courseIds={courseIds} sendRequest={sendRequest} handleClose={handleClose}/>
                                :<RegisteredCourses_ResultModal modal={modal} handleClose={handleClose}/>)}
          </>
          );
}
export default RegisteredCoursesPage;