import React from 'react';
import './App.css';
import LoadPDF from './components/LoadPDF';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <div>
          <h3> Images from a PDF</h3>
        </div>
      </header>
      <div className="App-container">
        <LoadPDF />
      </div>
    </div>
  );
}

export default App;
