import React from "react";

function NotFound() {
    return (
        <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap justify-content-between">
            <header>
            </header>
            <main>
                <div id="main-wrapper"
                     className="bg-linen row container-fluid px-4 px-xl-5 py-0 mb-0  justify-content-between">
                    <div id="error-text"
                         className="col-6 my-5 fw-bold d-flex flex-column flex-nowrap justify-content-center">
                        <div className="color-brown">Oops!</div>
                        <div className="color-red">404 | PAGE NOT FOUND</div>
                        <div>
                            <div>The page you are looking for might have been removed,</div>
                            <div>had its name changed or is temporarily unavailable.</div>
                        </div>
                        <div className="mt-4">
                            <a href="/" className="bg-brown color-linen px-5 py-3 rounded-5">GO TO
                                HOMEPAGE</a>
                        </div>
                    </div>
                    <div id="logo" className="col-6 color-brown mt-5 mb-4 fs-2 fw-bold">
                        <span id="baloot-logo"></span>
                    </div>
                </div>
            </main>
            <footer>
            </footer>
        </div>
    );
}

export default NotFound