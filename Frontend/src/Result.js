import React, { useEffect, useState } from "react";
import "./App.css";

export const ResultValue = (props) => {

  const data = {
	  issueName: [],
	  specialization: []
  }

    for (let i = 0; i < props.userData.length; i++) {
		data.issueName.push(props?.userData[i]?.Issue?.Name);
		console.log("Issue name", data?.issueName)
    }

  return (
    <div className="box">
      <h6 className="result">Results</h6>
	  <h5>{data.issueName[1]}</h5>
    </div>
  );
};
