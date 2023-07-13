import React, {Component} from "react";
import {Modal, Button, Box, Typography, Input} from "@mui/material";
import axios from "axios";
import DiscountService from "../services/DiscountService";

const HISTORY_API = "http://localhost:8080/history";
const APPLY_DISCOUNT_API = "http://localhost:8080/discount";
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

class PayModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isModalOpen:true,
            buyListItems: [],
            discounts:[],
            discount:'',
            priceSum: 0
        }
        this.handleCloseModal = this.handleCloseModal.bind(this);
        this.handleAccept = this.handleAccept.bind(this);
        this.addToHistory = this.addToHistory.bind(this);
        this.applyDiscount = this.applyDiscount.bind(this);
        this.handleDiscount = this.handleDiscount.bind(this);
    }
    handleAccept = () => {
        this.addToHistory();
        this.handleCloseModal();
    }

    handleDecline = () => {
        this.handleCloseModal();
    }

    handleCloseModal = () => {
        this.setState({isModalOpen : false});
        window.location.reload();
    }

    handleDiscount(event){
        this.setState({discount : event.target.value});
    }

    applyDiscount(){
        const discountCode = this.state.discount;
        setTimeout(() => {
            axios.post(APPLY_DISCOUNT_API, discountCode)
                .then(response => {
                    if (response.data === "wrong discount alert")
                        alert("Your Discount Code is either Invalid or Expired");
                    else
                        this.setState({priceSum: response.data})
                })
                .catch((error) => {
                    console.log(error);
                })
        },75)
        console.log(this.state.priceSum);
    }

    addToHistory = () => {
        this.setState({currentCredit: localStorage.getItem("currentCredit")});
        const priceSum = this.state.priceSum;
        console.log(this.state.discount);

        const data = {"discountCode": this.state.discount, "priceSum": priceSum};

        axios.post(HISTORY_API, data)
            .then(response => {
                if (response.data === "inStock alert")
                    alert("inStock alert");
                else if (response.data === "not enough credit")
                    alert("not enough credit");
                else if (response.data === "success"){
                    console.log("success");
                }
                    //window.location.reload();
            })
            .catch((error) => {
                console.log(error)
            });
    }

    componentDidMount() {
        this.setState({priceSum : this.props.priceSum});
        DiscountService.getDiscounts().then((response) => {
            this.setState({discounts : response.data});
        })
        console.log(this.props.priceSum);
        console.log(this.state.priceSum);
    }

    render() {
        const {isModalOpen} = this.state;
        let {priceSum} = this.state;
        console.log("hello");
        return (
        <Modal
            open={isModalOpen}
            onClose={this.handleCloseModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                    Price Sum = {priceSum}
                </Typography>
                <Input type="text" placeholder="Enter your discount code" value={this.state.discount} onChange={this.handleDiscount}/>
                <Button onClick={this.applyDiscount}> Apply Discount </Button>
                <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                    Are you sure about this?
                </Typography>
                <Button onClick={this.handleAccept}>Accept</Button>
                <Button onClick={this.handleDecline}>Decline</Button>
            </Box>
        </Modal>
        );
    }
}

export default PayModal