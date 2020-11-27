export class polygonPoint{
    constructor(x,y){
        this.x=x
        this.y=y
    }
}
export class rectangle{
    constructor(x,y,width,height){
        this.x=x
        this.y=y
        this.width=width
        this.height=height
    }
}
export class polygon{
    constructor(p1,p2,p3,p4,p5){
        this.p1=p1
        this.p2=p2
        this.p3=p3
        this.p4=p4
        this.p5=p5
    }
}
export class line{
    constructor(p1,p2){
        this.p1=p1
        this.p2=p2
    }
}
export class ellipse{
    constructor(center,r1,r2){
        this.center=center
        this.r1=r1
        this.r2=r2
    }
}
