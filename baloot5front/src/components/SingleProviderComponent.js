import React from "react";
import axios from "axios";

import LoginRegisterCard from "../cards/LoginRegisterCard";
import AddToCartCard from "../cards/AddToCartCard";

const SINGLE_PROVIDER_API = "http://localhost:8080/provider";
const SINGLE_PROVIDER_COMMODITIES_API = "http://localhost:8080/commodities";

class SingleProviderComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            provider: {},
            commodities : [],
            currentUserNull : false
        }
    }

    currentUserControl() {
        if (localStorage.getItem('currentUsername') === 'null') {
            this.setState({currentUserNull:true});
        }
    }

    componentDidMount() {
        let providerId = localStorage.getItem("idProvider");

        axios.post(SINGLE_PROVIDER_API, providerId)
            .then(response => {
                this.setState({provider: response.data});
            })
        axios.post(SINGLE_PROVIDER_COMMODITIES_API, providerId)
            .then(response => {
                this.setState({commodities: response.data});
            })
        this.currentUserControl();
    }

    render() {
        const currentUsername = localStorage.getItem('currentUsername');

        return (
            <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap">
                <header>
                    <div id="header-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-1 d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div id="header-logo" className="d-flex flex-row flex-nowrap align-items-center">
                            <a href="/" id="baloot-logo"></a>
                            <a href="/" id="baloot-title"
                               className="color-copper ms-2 fw-semibold fs-4">Baloot</a>
                        </div>
                        <div id="search-bar" className="mx-auto d-flex flex-row align-items-center">
                            <input type="text" id="search-text" placeholder="search your product ..."
                                   className="bg-linen color-grey rounded-3"/>
                            <select id="search-select" className="ps-3 pe-2 bg-dun color-brown rounded-3 border-0">
                                <option>name</option>
                                <option>category</option>
                            </select>
                        </div>
                        <div>
                            {this.state.currentUserNull
                                ?
                                <LoginRegisterCard/>
                                :
                                <div id="user-cart">
                                    <div className="btn-toolbar wd-100" role="toolbar">
                                        <a href="#"
                                           className="btn color-brown col-5 d-flex justify-content-center align-items-center">{currentUsername}</a>
                                        <a href="#"
                                           className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                            <span className="color-copper me-auto">Cart</span>
                                            <span className="color-copper">0</span>
                                        </a>
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap align-items-center">
                        <div id="provider" className="main-item col-11 row g-0 px-5 py-4 rounded-3">
                            <div id="provider-image">
                                <img src={this.state.provider.image} alt="provider"/>
                            </div>
                            <div id="provider-name">
                                <span>{this.state.provider.name}</span>
                            </div>
                            <div id="provider-registryDate">
                                <span>{this.state.provider.registryDate}</span>
                            </div>
                        </div>
                        <h1>All Provider Commodities</h1>
                        <div id="commodities" className="main-item col-10">
                            <div className="row row-cols-4 g-4">
                                {
                                    this.state.commodities.map(
                                        commodity =>
                                            <div className="col">
                                                <div className="card h-100 px-2 py-3">
                                                    <div className="card-body py-0">
                                                        <div className="card-title color-brown m-0 fw-semibold">
                                                            <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id)}>
                                                                {commodity.name}
                                                            </div>
                                                        </div>
                                                        <div
                                                            className="card-text color-red fw-semibold">{commodity.inStock} left
                                                            in stock
                                                        </div>
                                                    </div>
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id, commodity.providerId)}>
                                                        <img src={commodity.image} className="card-img-top my-2" alt="commodity"/>
                                                    </div>
                                                    <div
                                                        className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                                        <span
                                                            className="color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                        <AddToCartCard commodityId={commodity.id} inStock={commodity.inStock}/>
                                                    </div>
                                                </div>
                                            </div>
                                    )
                                }
                            </div>
                        </div>
                    </div>
                </main>
                <footer>
                    <div id="footer-wrapper"
                         className="bg-brown color-white px-5 py-2 fw-semibold container-fluid d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div className="color-linen fs-5">2023 © VPT</div>
                    </div>
                </footer>
            </div>
        )
    }
}

export default SingleProviderComponent