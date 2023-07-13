import React from "react";
import axios from "axios";

const CART_API = "http://localhost:8080/cart";

class AddToCartCard extends React.Component {
    constructor(props) {
        super(props);

        this.hideButton = this.hideButton.bind(this);
        this.decreaseQuantity = this.decreaseQuantity.bind(this);
        this.increaseQuantity = this.increaseQuantity.bind(this);
        this.addToCart = this.addToCart.bind(this);

        this.state = {
            showButton: true,
            quantity: 0,
            buyType: "",
            commodityId: "",
            inStock: 0,
        }
    }

    hideButton = () => {
        this.setState({showButton: false});
        this.setState({quantity: this.state.quantity + 1})
        this.setState({buyType: "increase"});
        this.addToCart();
    }
    increaseQuantity = () => {
        if (this.state.quantity < this.state.inStock) {
            this.setState({quantity: this.state.quantity + 1})
            this.setState({buyType: "increase"});
            this.addToCart();
        }
    }
    decreaseQuantity = () => {
        if (this.state.quantity === 1) {
            this.setState({showButton: true})
        }
        this.setState({quantity: this.state.quantity - 1})
        this.setState({buyType: "decrease"});
        this.addToCart();
    }
    addToCart(){
        setTimeout(() => {
            const currentUsername = localStorage.getItem("currentUsername");
            const commodityId = this.state.commodityId;
            const buyType = this.state.buyType;
            const data = {"username": currentUsername, "commodityId": commodityId, "buyType": buyType};

            axios.post(CART_API, data)
                .then(response => {
                    console.log(response)
                })
                .catch((error) => {
                    console.log(error)
                });
        }, 88)
    }

    componentDidMount() {
        this.setState({commodityId: this.props.commodityId});
        this.setState({inStock: this.props.inStock});
    }

    render() {
        return (
            <div> { this.state.inStock === 0 ?
                <button type="button" onClick={this.hideButton}
                        className="add-to-cart btn color-brown px-3 py-2 rounded-4" disabled>add to cart
                </button>
                :
                this.state.showButton ?
                <button type="button" onClick={this.hideButton}
                        className="add-to-cart btn color-brown px-3 py-2 rounded-4">add to cart
                </button>
                :
                <div>
                    <button className="col-3 h-100 d-flex flex-column justify-content-center" onClick={this.decreaseQuantity}>-
                    </button>
                    <input type="number" name="number" className="col-3 h-100 d-flex flex-column justify-content-center" value={this.state.quantity}/>
                    <button className="col-3 h-100 d-flex flex-column justify-content-center" onClick={this.increaseQuantity}>+
                    </button>
                </div>
                }
            </div>
        )
    }
}

export default AddToCartCard