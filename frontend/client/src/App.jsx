import { Route, Routes } from "react-router-dom"
import { ToastContainer } from "react-toastify"
import { useDispatch, useSelector } from "react-redux"
import { useEffect } from "react"
import Login from "./pages/public/Login"
import Home from "./pages/public/Home"
import Loading from './components/common/Loading.jsx';
import Modal from "./components/common/Modal"
import path from "./ultils/path"
import { getCurrent, getWishlist } from "./redux/action"
import Layout from "./pages/public/layout"


function App() {
  const { isLoading, isShowModal, modalContent } = useSelector(
    (state) => state.app
  )

  const { token } = useSelector((state) => state.user)
  const dispatch = useDispatch()

  useEffect(() => {
    setTimeout(() => {
      dispatch(getCurrent())
      if (token) dispatch(getWishlist())
    }, 800)
  }, [token])

  return (
    <>
      {isShowModal && <Modal>{modalContent}</Modal>}
      {isLoading && (
        <div className="fixed top-0 left-0 right-0 z-[1000] bottom-0 bg-overlay-70 flex justify-center items-center">
          <Loading />
        </div>
      )}
      <Routes>
        <Route path={path.LAYOUT} element={<Layout />}>
          <Route path={path.HOME} element={<Home />} />
        </Route>
        <Route path={path.LOGIN} element={<Login />} />
        <Route path={path.INVALID} element={<Home />} />
      </Routes>
    </>
  )
}

export default App
