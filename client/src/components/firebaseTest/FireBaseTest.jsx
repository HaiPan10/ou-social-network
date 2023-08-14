import { useEffect, useState } from 'react';
import { fetchToken, onMessageListener } from '../../configs/Firebase';

export const FireBaseTest = () => {
    const [show, setShow] = useState(false);
    const [notification, setNotification] = useState({ title: '', body: '' });
    const [isTokenFound, setTokenFound] = useState(false);

    useEffect(() => {
        fetchToken(setTokenFound);
    }, []);

    onMessageListener().then((payload) => {
        setNotification({ title: payload.notification.title, body: payload.notification.body });
        setShow(true);
        console.log(payload);
    }).catch(err => console.log('failed: ', err));

    const onShowNotificationClicked = () => {
        setNotification({ title: "Notification", body: "This is a test notification" });
        setShow(true);
    }

    return (
        <div className="App">
            <header className="App-header">
                {isTokenFound && <h1> Notification permission enabled 👍🏻 </h1>}
                {!isTokenFound && <h1> Need notification permission ❗️ </h1>}
            </header>
        </div>
    );
}
