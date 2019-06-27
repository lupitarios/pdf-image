import React, { Component } from 'react'

export class LoadPDF extends Component {

    constructor(props){
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            file: '',
            error: '',
            msg: '',
            pages : 0,
            imagesNumber : 0,
            wordsNumber : 0,
            images : []
        }
        this.isHidden = true;
        
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
        }).then(response =>  { return response.json();})
        .then(responseData =>{ 
            console.log(responseData);
            console.log("pagesNumber->"+responseData.pagesNumber);
            this.setState({ 
                error:'', 
                msg: 'Sucessfully uploaded file', 
                wordsNumber : responseData.wordsNumber,
                pages : responseData.pagesNumber,
                imagesNumber : responseData.imagesNumber,
                images : responseData.images
            });
            
            this.isHidden = false;
            return responseData;
        })
       .catch(err => {
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
                    <label className="label-sentences">Words Number: {this.state.wordsNumber} </label><br />
                    <label className="label-sentences">Images Number:{this.state.imagesNumber}</label><br />
                    <label className="label-sentences">Pages:{this.state.pages}</label><br />
                </div>
            </div>
            <div className="right">
                {
                    this.state.images.map(image => (
                        <img src="{`data:image/jpeg;base64,${image.data}`} "/>
                    ))
                }
            </div>
        </div>
    )
  }
}

export default LoadPDF
