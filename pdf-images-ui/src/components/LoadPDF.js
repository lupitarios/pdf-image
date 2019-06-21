import React, { Component } from 'react'

export class LoadPDF extends Component {

    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            file: '',
            error: '',
            msg: ''
        }
        this.isHidden = true;
        this.pages = 0;
        this.imagesNumber = 0;
        this.wordsNumber = 0;
    }

    onFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    }

    handleSubmit(event){
        event.preventDefault();

        if(!this.state.file){
            this.setState({error: 'Please upload a PDF'})
            return;
        }

        if(this.state.file.size >= 5000000){
            this.setState({error:'File size exceeds limit of 5MB.'})
        }

        let data = new FormData();
        data.append('file', this.state.file);
        data.append('name', this.state.file.name);

        console.log("Data Send" + data);

        fetch('http://localhost:8081/api-pdf/images',{
            method: 'POST',
            body: data
        }).then(response =>  {
            var data = response.json();
            
            console.log("data -> " + data);
            this.setState({ error:'', msg: 'Sucessfully uploaded file' });
            this.wordsNumber = data.wordsNumber;
            this.pages = data.pagesNumber;
            this.isHidden = false;
        }).catch(err => {
            this.setState({ error: err });
            alert("error"+ err);
        });
    }

  render() {
    return (
    
        <div className="container">
            <div className="left">
                <div className="panel">
                    <form onSubmit={this.handleSubmit}>
                        <label className="label-sentences">
                            Choose your file to load images:
                            <input id="input" type="file" accept="application/pdf" onChange={this.onFileChange}  />
                        </label>
                        <br />
                        <button type="submit" >Load Images</button>
                    </form>
                </div>
               
                <div className="panel" hidden={this.isHidden}>
                    <br />
                    <label className="label-sentences">Words Number: {this.wordsNumber} </label><br />
                    <label className="label-sentences">Images Number:{this.imagesNumber}</label><br />
                    <label className="label-sentences">Pages:{this.pages}</label><br />
                </div>
            </div>
            <div className="right"></div>
        </div>
    )
  }
}

export default LoadPDF
