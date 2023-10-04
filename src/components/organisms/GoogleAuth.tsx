import React, { useEffect, useState } from 'react';
import { GoogleLogin, GoogleOAuthProvider } from '@react-oauth/google';

const GoogleAuth = () => {
  const [clientId, setClientId] = useState<string>('');

  useEffect(() => {
    const clientId = process.env.REACT_APP_GOOGLE_CLIENT_KEY;

    if (clientId) {
      setClientId(clientId);
    }
  }, []);

  return (
    <div>
      <GoogleOAuthProvider clientId={clientId}>
        <GoogleLogin
          onSuccess={(credentialResponse) => {
            console.log(credentialResponse);
          }}
          onError={() => {
            console.log('Login Failed');
          }}
        />
      </GoogleOAuthProvider>
    </div>
  );
};

export default GoogleAuth;
