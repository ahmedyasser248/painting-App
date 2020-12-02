
export class polygonPoint{
    constructor(x,y){
        this.x=x
        this.y=y
    }
}
export class rectangle{
    StrokeColor='#000000'
    fillColor='#FFFFFF'
    constructor(x,y,width,height,id){
        this.x=x
        this.y=y
        this.width=width
        this.height=height
        this.id=id
    }
}
export class polygon{
    constructor(polpoints=[],id){
        this.polpoints=polpoints
        this.id=id
    }

}
export class line{
    StrokeColor='#000000'
    constructor(p1x,p1y,p2x,p2y,id){
        this.p1x=p1x
        this.p2x=p2x
        this.p1y=p1y
        this.p2y=p2y
        this.id=id
    }
}
export class ellipse{
    StrokeColor='#000000'
    fillColor='#FFFFFF'
    constructor(x,y,r1,r2,id){
        this.x=x
        this.y=y
        this.r1=r1
        this.r2=r2
        this.id=id
    }
}

export class circle{
    StrokeColor='#000000'
    fillColor='#FFFFFF'
    constructor(x,y,r,id){
        this.x=x
        this.y=y
        this.r=r
        this.id=id
    }
}

export class square{
    StrokeColor='#000000'
    fillColor='#FFFFFF'
    constructor(x,y,length,id){
        this.x=x
        this.y=y
        this.length=length
        this.id=id
    }
}

export class triangle{
     StrokeColor='#000000'
    fillColor='#FFFFFF'
    constructor(polpoints=[],id){
        this.polpoints=polpoints
        this.id=id
    }

}
