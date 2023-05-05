import { useState } from "react";

function ErrorComponent({ errors }) {
    return (
      <div>
        {errors.map((error, index) => (
          <p key={index} className="py-2 text-red-500 text-sm">{error}</p>
        ))}
      </div>
    );
  }


  export default ErrorComponent