import React, { Component} from 'react';
import { Form } from 'react-bootstrap';
import User from '.././user/User';

class Membership extends Component {
	constructor(props) {
		super(props);
		this.state = {
      error: '',
      membership: {}
    }
  }

  async componentDidMount () { 
   /* try {
      let response = await (await fetch(`http://localhost:8081/membership/getMembership/${User.username}`, {
        method: 'get',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'X-Auth-Token': localStorage.getItem("token")
        }
      })).json();
      console.log(response)
      if (response.status === 'notcreated') {
        this.setState({
          membership: {}
        })
      } else if (response.status !== 'error'){
        this.setState({
          membership: response
        })
      }
    } catch (err) {
      this.setState({
        errors: err.toString()
      });
    }*/
  }

  payMembership = async (e) => {
    e.preventDefault();
    console.log("user email", User.email);
    try {
      let response = await (await fetch(`http://localhost:8081/membership/payBill/${User.username}`, {
        method: 'post',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          //'Authorization': 'Bearer ' + localStorage.getItem("token"),
          'X-Auth-Token': localStorage.getItem("token")
        }
      })).json();
      console.log(response)
      if (response.status == 'fail') {
        alert('Something went wrong');
      } else if (response.status == "success") {
        if (response.data) {
          window.open(
            response.data,
            '_blank' 
          );
        }
      }
    } catch (err) {
      this.setState({
        errors: err.toString()
      });
    }
  }

  render () {
    return (
      <div>
        <div className="margin-top-page">
          <div className="d-flex flex-row mt-2 ml-3">
            <div className="p-3" >
              <Form className="needs-validation" role="form"  onSubmit={this.payMembership}>
                <p><label>Membership price: 12</label></p>
                {this.state.membership && this.state.membership.active ?
                <p><label>Membership status: 'activated' </label></p>
                :
                <p><label>Membership status: 'inactive'</label></p>
                }
                <br/>
                {this.state.membership && this.state.membership.active ? 
                  <p></p>
                  :
                  <button className="btn btn-primary" type="submit" /*onClick={this.payMembership}*/>Pay Membership</button>
                }
              </Form>
            </div>
          </div>
        </div>
      </div>
    )
  }
}


export default Membership;
