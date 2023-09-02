
import React from 'react';
import moment from 'moment';
import './countDown.scss'

class CountDown extends React.Component {
    state = {
        days: undefined,
        hours: undefined,
        minutes: undefined,
        seconds: undefined
    };
    
    componentDidMount() {
        this.interval = setInterval(() => {
            const { timeTillDate, timeFormat } = this.props;
            const then = moment(timeTillDate, "DD-MM-YYYY HH:mm:ss").valueOf();
            const now = moment();
            const countdown = then - now;
            const days = Math.floor(countdown / (1000 * 60 * 60 * 24));
            const hours = Math.floor((countdown % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((countdown % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((countdown % (1000 * 60)) / 1000);
            this.setState({ days, hours, minutes, seconds });
        }, 1000);
    }
    
    componentWillUnmount() {
        if (this.interval) {
            clearInterval(this.interval);
        }
    }
    
    render() {
        const { days, hours, minutes, seconds } = this.state;
        
        // Mapping the date values to radius values
        const daysRadius = mapNumber(days, 30, 0, 0, 360);
        const hoursRadius = mapNumber(hours, 24, 0, 0, 360);
        const minutesRadius = mapNumber(minutes, 60, 0, 0, 360);
        const secondsRadius = mapNumber(seconds, 60, 0, 0, 360);
        
        if (!seconds) {
            return null;
        }
        
        return (
            <div>
                <div className="countdown-wrapper">
                    {days && (
                        <div className="countdown-item">
                            {/* <SVGCircle radius={daysRadius} /> */}
                            {days}
                            <span>ngày</span>
                        </div>
                    )}
                    {hours && (
                        <div className="countdown-item">
                            {/* <SVGCircle radius={hoursRadius} /> */}
                            {hours}
                            <span>giờ</span>
                        </div>
                    )}
                    {minutes && (
                        <div className="countdown-item">
                            {/* <SVGCircle radius={minutesRadius} /> */}
                            {minutes}
                            <span>phút</span>
                        </div>
                    )}
                    {seconds && (
                        <div className="countdown-item">
                            {/* <SVGCircle radius={secondsRadius} /> */}
                            {seconds}
                            <span>giây</span>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

const SVGCircle = ({ radius }) => (
    <svg className="countdown-svg">
        <path
            fill="none"
            stroke="#ffffff"
            strokeWidth="4"
            d={describeArc(50, 50, 48, 0, radius)}
        />
    </svg>
);

function polarToCartesian(centerX, centerY, radius, angleInDegrees) {
    var angleInRadians = ((angleInDegrees - 90) * Math.PI) / 180.0;
    
    return {
        x: centerX + radius * Math.cos(angleInRadians),
        y: centerY + radius * Math.sin(angleInRadians)
    };
}

function describeArc(x, y, radius, startAngle, endAngle) {
    var start = polarToCartesian(x, y, radius, endAngle);
    var end = polarToCartesian(x, y, radius, startAngle);
    var largeArcFlag = endAngle - startAngle <= 180 ? '0' : '1';
    var d = [
        'M',
        start.x,
        start.y,
        'A',
        radius,
        radius,
        0,
        largeArcFlag,
        0,
        end.x,
        end.y
    ].join(' ');
    
    return d;
}

function mapNumber(number, in_min, in_max, out_min, out_max) {
    return (
        ((number - in_min) * (out_max - out_min)) / (in_max - in_min) + out_min
    );
}

export default CountDown;