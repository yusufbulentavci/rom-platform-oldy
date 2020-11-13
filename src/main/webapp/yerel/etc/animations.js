{
    "bounce": {
        "transform-origin": "center bottom",
        "f": {
            "70%": {
                "animation-timing-function": "cubic-bezier(0.755, 0.050, 0.855, 0.060)",
                "transform": "translate3d(0, -15px, 0)"
            },
            "90%": {
                "transform": "translate3d(0,-4px,0)"
            },
            "from,20%,53%,80%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355, 1.000)",
                "transform": "translate3d(0,0,0)"
            },
            "40%,43%": {
                "animation-timing-function": "cubic-bezier(0.755, 0.050, 0.855, 0.060)",
                "transform": "translate3d(0, -30px, 0)"
            }
        }
    },
    "pulse": {
        "f": {
            "from,50%,100%": {
                "opacity": "1"
            },
            "25%,75%": {
                "opacity": "0"
            }
        }
    },
    "flash": {
        "f": {
            "50%": {
                "transform": "scale3d(1.05, 1.05, 1.05)"
            },
            "100%": {
                "transform": "scale3d(1, 1, 1)"
            },
            "from": {
                "transform": "scale3d(1, 1, 1)"
            }
        }
    },
    "rubberBand": {
        "f": {
            "30%": {
                "transform": "scale3d(1.25, 0.75, 1)"
            },
            "40%": {
                "transform": "scale3d(0.75, 1.25, 1)"
            },
            "50%": {
                "transform": "scale3d(1.15, 0.85, 1)"
            },
            "65%": {
                "transform": "scale3d(.95, 1.05, 1)"
            },
            "75%": {
                "transform": "scale3d(1.05, .95, 1)"
            },
            "100%": {
                "transform": "scale3d(1, 1, 1)"
            },
            "from": {
                "transform": "scale3d(1, 1, 1)"
            }
        }
    },
    "shake": {
        "f": {
            "from,100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "10%,30%,50%,70%,90%": {
                "transform": "translate3d(-10px, 0, 0)"
            },
            "20%,40%,60%,80%": {
                "transform": "translate3d(10px, 0, 0)"
            }
        }
    },
    "swing": {
        "transform-origin": "top center",
        "f": {
            "20%": {
                "transform": "rotate3d(0, 0, 1, 15deg)"
            },
            "40%": {
                "transform": "rotate3d(0, 0, 1, -10deg)"
            },
            "60%": {
                "transform": "rotate3d(0, 0, 1, 5deg)"
            },
            "80%": {
                "transform": "rotate3d(0, 0, 1, -5deg)"
            },
            "100%": {
                "transform": "rotate3d(0, 0, 1, 0deg)"
            }
        }
    },
    "tada": {
        "f": {
            "100%": {
                "transform": "scale3d(1, 1, 1)"
            },
            "from": {
                "transform": "scale3d(1, 1, 1)"
            },
            "10%,20%": {
                "transform": "scale3d(.9, .9, .9) rotate3d(0, 0, 1, -3deg)"
            },
            "30%,50%,70%,90%": {
                "transform": "scale3d(1.1, 1.1, 1.1) rotate3d(0, 0, 1, 3deg)"
            }
        }
    },
    "wobble": {
        "f": {
            "15%": {
                "transform": "translate3d(-25%, 0, 0) rotate3d(0, 0, 1, -5deg)"
            },
            "30%": {
                "transform": "translate3d(20%, 0, 0) rotate3d(0, 0, 1, 3deg)"
            },
            "45%": {
                "transform": "translate3d(-15%, 0, 0) rotate3d(0, 0, 1, -3deg)"
            },
            "60%": {
                "transform": "translate3d(-25%, 0, 0) rotate3d(0, 0, 1, -5deg)"
            },
            "75%": {
                "transform": "translate3d(10%, 0, 0) rotate3d(0, 0, 1, 2deg)"
            },
            "100%": {
                "transform": null
            },
            "from": {
                "transform": null
            }
        }
    },
    "jello": {
        "transform-origin": "center",
        "f": {
            "22%": {
                "transform": "skewX(-12.5deg) skewY(-12.5deg)"
            },
            "33%": {
                "transform": "skewX(6.25deg) skewY(6.25deg)"
            },
            "44%": {
                "transform": "skewX(-3.125deg) skewY(-3.125deg)"
            },
            "55%": {
                "transform": "skewX(1.5625deg) skewY(1.5625deg)"
            },
            "67%": {
                "transform": "skewX(-0.78125deg) skewY(-0.78125deg)"
            },
            "78%": {
                "transform": "skewX(0.390625deg) skewY(0.390625deg)"
            },
            "89%": {
                "transform": "skewX(-0.1953125deg) skewY(-0.1953125deg)"
            },
            "from,11%,100%": {
                "transform": null
            }
        }
    },
    "bounceIn": {
        "f": {
            "0%": {
                "opacity": "0",
                "transform": "scale3d(.3, .3, .3)"
            },
            "20%": {
                "transform": "scale3d(1.1, 1.1, 1.1)"
            },
            "40%": {
                "transform": "scale3d(.9, .9, .9)"
            },
            "60%": {
                "opacity": "1",
                "transform": "scale3d(1.03, 1.03, 1.03)"
            },
            "80%": {
                "transform": "scale3d(.97, .97, .97)"
            },
            "100%": {
                "opacity": "1",
                "transform": "scale3d(1, 1, 1)"
            },
            "from,20%,40%,60%,80%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355, 1.000)"
            }
        }
    },
    "bounceInDown": {
        "f": {
            "0%": {
                "opacity": "0",
                "transform": "translate3d(0, -3000px, 0)"
            },
            "60%": {
                "opacity": "1",
                "transform": "translate3d(0, 25px, 0)"
            },
            "75%": {
                "transform": "translate3d(0, -10px, 0)"
            },
            "90%": {
                "transform": "translate3d(0, 5px, 0)"
            },
            "100%": {
                "transform": null
            },
            "from,60%,75%,90%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355, 1.000)"
            }
        }
    },
    "bounceInLeft": {
        "f": {
            "0%": {
                "opacity": "0",
                "transform": "translate3d(-3000px, 0, 0)"
            },
            "60%": {
                "opacity": "1",
                "transform": "translate3d(25px, 0, 0)"
            },
            "75%": {
                "transform": "translate3d(-10px, 0, 0)"
            },
            "90%": {
                "transform": "translate3d(5px, 0, 0)"
            },
            "100%": {
                "transform": null
            },
            "from,60%,75%,90%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355,  1.000)"
            }
        }
    },
    "bounceInRight": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "translate3d(-25px, 0, 0)"
            },
            "75%": {
                "transform": "translate3d(10px, 0, 0)"
            },
            "90%": {
                "transform": "translate3d(-5px, 0, 0)"
            },
            "100%": {
                "transform": null
            },
            "from,60%,75%,90%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355,  1.000)"
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(3000px, 0, 0)"
            }
        }
    },
    "bounceInUp": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "translate3d(0, -20px, 0)"
            },
            "75%": {
                "transform": "translate3d(0, 10px, 0)"
            },
            "90%": {
                "transform": "translate3d(0, -5px, 0)"
            },
            "100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "from,60%,75%,90%,100%": {
                "animation-timing-function": "cubic-bezier(0.215, 0.610, 0.355, 1.000)"
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(0, 3000px, 0)"
            }
        }
    },
    "bounceOut": {
        "f": {
            "20%": {
                "transform": "scale3d(.9, .9, .9)"
            },
            "100%": {
                "opacity": "0",
                "transform": "scale3d(.3, .3, .3)"
            },
            "50%,55%": {
                "opacity": "1",
                "transform": "scale3d(1.1, 1.1, 1.1)"
            }
        }
    },
    "bounceOutDown": {
        "f": {
            "20%": {
                "transform": "translate3d(0, 10px, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, 2000px, 0)"
            },
            "40%,45%": {
                "opacity": "1",
                "transform": "translate3d(0, -20px, 0)"
            }
        }
    },
    "bounceOutLeft": {
        "f": {
            "20%": {
                "opacity": "1",
                "transform": "translate3d(20px, 0, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "translate3d(-2000px, 0, 0)"
            }
        }
    },
    "bounceOutRight": {
        "f": {
            "20%": {
                "opacity": "1",
                "transform": "translate3d(-20px, 0, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "translate3d(2000px, 0, 0)"
            }
        }
    },
    "bounceOutUp": {
        "f": {
            "20%": {
                "transform": "translate3d(0, -10px, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, -2000px, 0)"
            },
            "40%,45%": {
                "opacity": "1",
                "transform": "translate3d(0, 20px, 0)"
            }
        }
    },
    "fade": {
        "f": {
            "100%": {
                "opacity": "1"
            },
            "from": {
                "opacity": "0"
            }
        }
    },
    "fadeInDown": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(0, -100%, 0)"
            }
        }
    },
    "fadeInDownBig": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(0, -2000px, 0)"
            }
        }
    },
    "fadeInLeft": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(-100%,0, 0)"
            }
        }
    },
    "fadeInLeftBig": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(-2000px, 0, 0)"
            }
        }
    },
    "fadeInRight": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(100%, 0, 0)"
            }
        }
    },
    "fadeInRightBig": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(2000px, 0, 0)"
            }
        }
    },
    "fadeInUp": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(0, 100%, 0)"
            }
        }
    },
    "fadeInUpBig": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform": "translate3d(0, 2000px, 0)"
            }
        }
    },
    "fadeOut": {
        "f": {
            "100%": {
                "opacity": "0"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutDown": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, 100%, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutDownBig": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, 2000px, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutLeft": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(-100%, 0, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutLeftBig": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(-2000px, 0, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutRight": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(100%, 0, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutRightBig": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(2000px, 0, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutUp": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, -100%, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "fadeOutUpBig": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(0, -2000px, 0)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "flip": {
        "backface-visibility": "visible",
        "f": {
            "40%": {
                "animation-timing-function": "ease-out",
                "transform": "perspective(400px)translate3d(0, 0, 150px)rotate3d(0, 1, 0, -190deg)"
            },
            "50%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)translate3d(0, 0, 150px)rotate3d(0, 1, 0, -170deg)"
            },
            "80%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)scale3d(.95, .95, .95)"
            },
            "100%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)"
            },
            "from": {
                "animation-timing-function": "ease-out",
                "transform": "perspective(400px)rotate3d(0, 1, 0, -360deg)"
            }
        }
    },
    "flipInX": {
        "backface-visibility": "visible!important",
        "f": {
            "40%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)rotate3d(1, 0, 0, -20deg)"
            },
            "60%": {
                "opacity": "1",
                "transform": "perspective(400px)rotate3d(1, 0, 0, 10deg)"
            },
            "80%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)rotate3d(1, 0, 0, -5deg)"
            },
            "100%": {
                "transform": "perspective(400px)"
            },
            "from": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)rotate3d(1, 0, 0, 90deg)",
                "opacity": "0"
            }
        }
    },
    "flipInY": {
        "backface-visibility": "visible!important",
        "f": {
            "40%": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)rotate3d(0, 1, 0, -20deg)"
            },
            "60%": {
                "opacity": "1",
                "transform": "perspective(400px)rotate3d(0, 1, 0, 10deg)"
            },
            "80%": {
                "transform": "perspective(400px)rotate3d(0, 1, 0, -5deg)"
            },
            "100%": {
                "transform": "perspective(400px)"
            },
            "from": {
                "animation-timing-function": "ease-in",
                "transform": "perspective(400px)rotate3d(0, 1, 0, 90deg)",
                "opacity": "0"
            }
        }
    },
    "fadeOutX": {
        "backface-visibility": "visible!important",
        "f": {
            "30%": {
                "opacity": "1",
                "transform": "perspective(400px)rotate3d(1, 0, 0, -20deg)"
            },
            "100%": {
                "opacity": "0",
                "transform": "perspective(400px)rotate3d(1, 0, 0, 90deg)"
            },
            "from": {
                "transform": "perspective(400px)"
            }
        }
    },
    "lightSpeedIn": {
        "animation-timing-function": "ease-out",
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "skewX(20deg)"
            },
            "80%": {
                "opacity": "1",
                "transform": "skewX(-5deg)"
            },
            "100%": {
                "opacity": "1",
                "transform": null
            },
            "from": {
                "transform": "translate3d(100%, 0, 0)skewX(-30deg)",
                "opacity": "0"
            }
        }
    },
    "lightSpeedout": {
        "animation-timing-function": "ease-in",
        "f": {
            "100%": {
                "opacity": "0",
                "transform": "translate3d(100%, 0, 0)skewX(30deg)"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "rotateIn": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform-origin": "center",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform-origin": "center",
                "transform": "rotate3d(0, 0, 1, -200deg)"
            }
        }
    },
    "rotateInDownLeft": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform-origin": "leftbottom",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform-origin": "leftbottom",
                "transform": "rotate3d(0, 0, 1, -45deg)"
            }
        }
    },
    "rotateInDownRight": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform-origin": "rightbottom",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform-origin": "rightbottom",
                "transform": "rotate3d(0, 0, 1, 45deg)"
            }
        }
    },
    "rotateInUpLeft": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform-origin": "leftbottom",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform-origin": "leftbottom",
                "transform": "rotate3d(0, 0, 1, 45deg)"
            }
        }
    },
    "rotateInUpRight": {
        "f": {
            "100%": {
                "opacity": "1",
                "transform-origin": "rightbottom",
                "transform": null
            },
            "from": {
                "opacity": "0",
                "transform-origin": "rightbottom",
                "transform": "rotate3d(0, 0, 1, -90deg)"
            }
        }
    },
    "rotateOut": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform-origin": "center",
                "transform": "rotate3d(0, 0, 1, 200deg)"
            },
            "from": {
                "opacity": "1",
                "transform-origin": "center"
            }
        }
    },
    "rotateOutDownLeft": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform-origin": "leftbottom",
                "transform": "rotate3d(0, 0, 1, 45deg)"
            },
            "from": {
                "opacity": "1",
                "transform-origin": "leftbottom"
            }
        }
    },
    "rotateOutDownRight": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform-origin": "rightbottom",
                "transform": "rotate3d(0, 0, 1, -45deg)"
            },
            "from": {
                "opacity": "1",
                "transform-origin": "rightbottom"
            }
        }
    },
    "rotateOutUpLeft": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform-origin": "leftbottom",
                "transform": "rotate3d(0, 0, 1, -45deg)"
            },
            "from": {
                "opacity": "1",
                "transform-origin": "leftbottom"
            }
        }
    },
    "rotateOutUpRight": {
        "f": {
            "100%": {
                "opacity": "0",
                "transform-origin": "rightbottom",
                "transform": "rotate3d(0, 0, 1, 90deg)"
            },
            "from": {
                "opacity": "1",
                "transform-origin": "rightbottom"
            }
        }
    },
    "hinge": {
        "f": {
            "0%": {
                "animation-timing-function": "ease-in-out",
                "transform-origin": "topleft"
            },
            "100%": {
                "animation-timing-function": "ease-in-out",
                "transform-origin": "topleft",
                "transform": "rotate3d(0, 0, 1, 60deg)",
                "opacity": "1"
            },
            "20%,60%": {
                "animation-timing-function": "ease-in-out",
                "transform": "rotate3d(0, 0, 1, 80deg)",
                "transform-origin": "topleft"
            },
            "40%,80%": {
                "animation-timing-function": "ease-in-out",
                "transform": "rotate3d(0, 0, 1, 60deg)",
                "transform-origin": "topleft",
                "opacity": "1"
            }
        }
    },
    "rollIn": {
        "f": {
            "0%": {
                "opacity": "0",
                "transform": "translate3d(-100%, 0, 0)rotate3d(0, 0, 1, -120deg)"
            },
            "100%": {
                "transform": null,
                "opacity": "1"
            }
        }
    },
    "rollOut": {
        "f": {
            "100%": {
                "transform": "translate3d(100%, 0, 0)rotate3d(0, 0, 1, 120deg)",
                "opacity": "0"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "zoomIn": {
        "f": {
            "50%": {
                "opacity": "1"
            },
            "from": {
                "opacity": "0",
                "transform": "scale3d(.3, .3, .3)"
            }
        }
    },
    "zoomInDown": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(0, 60px, 0)",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)"
            },
            "from": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(0, -1000px, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            }
        }
    },
    "zoomInLeft": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(10px, 0, 0)",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)"
            },
            "from": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(-1000px, 0, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            }
        }
    },
    "zoomInRight": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(-10px, 0, 0)",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)"
            },
            "from": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(1000px, 0, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            }
        }
    },
    "zoomInUp": {
        "f": {
            "60%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(0, -60px, 0)",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)"
            },
            "from": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(0, 1000px, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            }
        }
    },
    "zoomOut": {
        "f": {
            "50%": {
                "opacity": "0",
                "transform": "scale3d(.3, .3, .3)"
            },
            "100%": {
                "opacity": "0"
            },
            "from": {
                "opacity": "1"
            }
        }
    },
    "zoomOutDown": {
        "f": {
            "40%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(0, -60px, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            },
            "100%": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(0, 2000px, 0)",
                "transform-origin": "centerbottom",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)"
            }
        }
    },
    "zoomOutLeft": {
        "f": {
            "40%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(42px, 0, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "scale(.1)translate3d(-2000px, 0, 0)",
                "transform-origin": "leftcenter"
            }
        }
    },
    "zoomOutRight": {
        "f": {
            "40%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(-42px, 0, 0)"
            },
            "100%": {
                "opacity": "0",
                "transform": "scale(.1)translate3d(2000px, 0, 0)",
                "transform-origin": "rightcenter"
            }
        }
    },
    "zoomOutUp": {
        "f": {
            "40%": {
                "opacity": "1",
                "transform": "scale3d(.475, .475, .475)translate3d(0, 60px, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            },
            "100%": {
                "opacity": "0",
                "transform": "scale3d(.1, .1, .1)translate3d(0, -2000px, 0)",
                "animation-timing-function": "cubic-bezier(0.175, 0.885, 0.320, 1)",
                "transform-origin": "centerbottom"
            }
        }
    },
    "slideInDown": {
        "f": {
            "100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "from": {
                "visibility": "visible",
                "transform": "translate3d(0, -100%, 0)",
                "animation-timing-function": "cubic-bezier(0.550, 0.055, 0.675, 0.190)"
            }
        }
    },
    "slideInLeft": {
        "f": {
            "100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "from": {
                "visibility": "visible",
                "transform": "translate3d(-100%, 0, 0)"
            }
        }
    },
    "slideInRight": {
        "f": {
            "100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "from": {
                "visibility": "visible",
                "transform": "translate3d(100%, 0, 0)"
            }
        }
    },
    "slideInUp": {
        "f": {
            "100%": {
                "transform": "translate3d(0, 0, 0)"
            },
            "from": {
                "visibility": "visible",
                "transform": "translate3d(0, 100%, 0)"
            }
        }
    },
    "slideOutDown": {
        "f": {
            "100%": {
                "visibility": "hidden",
                "transform": "translate3d(0, 100%, 0)"
            },
            "from": {
                "transform": "translate3d(0, 0, 0)"
            }
        }
    },
    "slideOutLeft": {
        "f": {
            "100%": {
                "visibility": "hidden",
                "transform": "translate3d(-100%, 0, 0)"
            },
            "from": {
                "transform": "translate3d(0, 0, 0)"
            }
        }
    },
    "slideOutRight": {
        "f": {
            "100%": {
                "visibility": "hidden",
                "transform": "translate3d(100%, 0, 0)"
            },
            "from": {
                "transform": "translate3d(0, 0, 0)"
            }
        }
    },
    "slideOutUp": {
        "f": {
            "100%": {
                "visibility": "hidden",
                "transform": "translate3d(0, -100%, 0)"
            },
            "from": {
                "transform": "translate3d(0, 0, 0)"
            }
        }
    }
}