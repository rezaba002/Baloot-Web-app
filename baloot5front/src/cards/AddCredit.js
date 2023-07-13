import React from "react";
import axios from "axios";
import {Box, Button, Modal, Typography} from "@mui/material";

const CREDIT_API = "http://localhost:8080/credit"
localStorage.setItem("creditAccepted","false");


const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

class AddCredit extends React.Component {
    constructor(props) {
        super(props);
        
        this.handleCredit = this.handleCredit.bind(this);
        this.addCredit = this.addCredit.bind(this);
        this.handleCloseModal = this.handleCloseModal.bind(this);
        this.handleOpenModal = this.handleOpenModal.bind(this);

        this.state = {
            credit: 0,
            isModalOpen: false
        }
    }

    handleCredit(event) {
        this.setState({credit: event.target.value});
    }

    handleOpenModal = () => {
        this.setState({ isModalOpen: true });
        console.log(this.state.isModalOpen);
    };

    handleCloseModal = () => {
        console.log(this.state.isModalOpen);
        this.setState({ isModalOpen: false });
        this.addCredit();
    };

    handleCloseAccept = () => {
        this.setState({ isModalOpen: false });
        this.addCredit();
    }

    handleCloseDecline = () => {
        this.setState({ isModalOpen: false });
    }

    addCredit = () => {
        const credit = this.state.credit;
        axios.post(CREDIT_API, credit)
            .then(response => {
                localStorage.setItem("currentCredit", response.data);
                window.location.reload();
            })
            .catch((error) => {
                console.log(error);
            })
    }

    componentDidMount() {
    }

    render() {
        const {isModalOpen} = this.state;
        return(
            <div>
            <form className="col-12">
            <input type="number" placeholder="Amount T" value={this.state.credit} onChange={this.handleCredit}
                   className="col-12 mb-2 border rounded-3 fw-bold text-center"/>
                <button type="submit" onClick={(event) =>
                {event.preventDefault();
                this.setState({ isModalOpen: true });
                }}
                        className="col-12 btn bg-brown color-linen px-4 py-2 rounded-3 fs-5 fw-bold">Add More Credit
                </button>
                <div>
                <Modal
                    open = {isModalOpen}
                    onClose = {this.handleCloseModal}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                <div>
                    <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                    Are you sure about this?
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                    </Typography>
                    <Button onClick={this.handleCloseAccept}>Accept</Button>
                    <Button onClick={this.handleCloseDecline}>Decline</Button>
                    </Box>
                </div>
                </Modal>
                </div>
            </form>
            </div>
        )
    }
}

export default AddCredit