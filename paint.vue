<template>
<div>yala bena</div>
<button @click="ChangeTool('')" type="button">Select</button>
 <canvas id ="my-canvas" width="600" height="600"></canvas>   
</template>

<script>
import {polygonPoint,square,circle,rectangle, line,ellipse,polygon} from './classes.js'

export default {
    name:'paintp',
    
    data() {
        return {
            counter:0,
            shapes :[],
            polPoints:[],
            recentShape:'',
            currentTool:'polygon',
            canvas:'',
            ctx:'',
            savedImageData:'',
            strokeColor:'black',
            line_Width:2,
            fillColor:'black',
            canvasWidth:600,
            canvasHeight:600,
            polygonSides:6,
            usingBrush:false,
            brushXPoints:[],
            brushYPoints:[],
            brushDownPos:[],
            ShapeBoundingBox:{
                constructor(left,top,width,height){
                    this.left=left
                    this.top=top
                    this.width=width
                    this.height=height
                }
            
        },
        MouseDownPos:{
        constructor(x,y) {
            this.x = x
            this.y = y
        }
     },
       Location:{
             constructor(x,y) {
                this.x = x
                this.y = y
         }
        },
            
        }
    },
    mounted() {
        let $vm=this;
        $vm.ShapeBoundingBox.constructor(0,0,0,0)
        $vm.MouseDownPos.constructor(0,0)
        $vm.Location.constructor(0,0)
        var c=document.getElementById('my-canvas')
        $vm.canvas=c
        $vm.ctx=$vm.canvas.getContext('2d')
        $vm.ctx.strokeStyle=$vm.strokeColor
        $vm.ctx.line_Width=$vm.line_Width
            $vm.canvas.addEventListener("mousedown",$vm.ReactToMouseDown)
            $vm.canvas.addEventListener("mousemove",$vm.ReactToMouseMove)
            $vm.canvas.addEventListener("mouseup",$vm.ReactToMouseUp)
    },
    methods:{
        ChangeTool(toolClicked){
            this.currentTool=toolClicked
            console.log(toolClicked)
        },
        getMousePosition(x,y){
            let canvasSizeData=this.canvas.getBoundingClientRect()
            return{x: (x - canvasSizeData.left) * (this.canvas.width / canvasSizeData.width),
        y: (y - canvasSizeData.top)  * (this.canvas.height / canvasSizeData.height)
            }
        },
        saveCanvasImage(){
            this.savedImageData=this.ctx.getImageData(0,0,this.canvas.width,this.canvas.height)
        },
        RedrawCanvasImage(){
            this.ctx.putImageData(this.savedImageData,0,0)
        },
        updateRubberBandSizeData(){
            this.ShapeBoundingBox.width = Math.abs(this.Location.x - this.MouseDownPos.x)
           this.ShapeBoundingBox.height=Math.abs(this.Location.y-this.MouseDownPos.y)
            if(this.Location.x>this.MouseDownPos.x){
                this.ShapeBoundingBox.left=this.MouseDownPos.x
            }
            else{
                this.ShapeBoundingBox.left=this.Location.x
            }
            if(this.Location.y>this.MouseDownPos.y){
                this.ShapeBoundingBox.top=this.MouseDownPos.y
            }
            else{
                this.ShapeBoundingBox.top=this.Location.y
            }
        },
        getAngleByXandY(x,y){
            let adjacent = this.MouseDownPos.x-x
            let opposite=this.MouseDownPos.y-y
            return this.radiansToDegrees(Math.atan2(opposite,adjacent))
        },
        radiansToDegrees(rad){
            if(rad<0){
        return (360.0 + (rad * (180 / Math.PI))).toFixed(2)
            }
            else{
                return (rad * (180 / Math.PI)).toFixed(2);
            }
        },
        degreesToRadians(degrees){
            return degrees *(Math.PI/180)
        },
        getPolygonPoints(){
            let angle = this.degreesToRadians(this.getAngleByXandY(this.Location.x,this.Location.y))
            let radiusX = this.ShapeBoundingBox.width
            let radiusY = this.ShapeBoundingBox.height
            let polygonPoints=[]
            for(let i = 0; i < this.polygonSides; i++){
                    var pp=new polygonPoint(this.Location.x+radiusX*Math.sin(angle),this.Location.y-radiusY*Math.cos(angle))
                    polygonPoints.push(pp)
                    angle=angle+2*Math.PI/this.polygonSides
            }
            return polygonPoints
        },
        getPolygon(){
            let polygonPoints=this.getPolygonPoints()
            this.polPoints=polygonPoints
            //console.log(polygonPoints)
            this.ctx.beginPath()
            this.ctx.moveTo(polygonPoints[0].x,polygonPoints[0].y)
            for(let i=1;i<this.polygonSides; i++){
                this.ctx.lineTo(polygonPoints[i].x,polygonPoints[i].y)
            }
            this.ctx.closePath()
        },
        drawRubberbandShape(loc){
            this.ctx.strokeStyle=this.strokeColor
            this.ctx.fillStyle=this.fillColor
            if(this.currentTool==='brush'){
                this.drawBrush()
            }
            else if(this.currentTool==="line"){
                this.ctx.beginPath()
                this.ctx.moveTo(this.MouseDownPos.x,this.MouseDownPos.y)
                this.ctx.lineTo(loc.x,loc.y)
                this.ctx.stroke()
            }else if(this.currentTool==="rectangle"){
                this.ctx.strokeRect(this.ShapeBoundingBox.left,this.ShapeBoundingBox.top,this.ShapeBoundingBox.width,this.ShapeBoundingBox.height)
            }else if(this.currentTool==="square"){
                this.ctx.strokeRect(this.ShapeBoundingBox.left,this.ShapeBoundingBox.top,this.ShapeBoundingBox.width,this.ShapeBoundingBox.width)
            }
            else if(this.currentTool==="circle"){
                let radius =this.ShapeBoundingBox.width
                this.ctx.beginPath()
                this.ctx.arc(this.MouseDownPos.x,this.MouseDownPos.y,radius,0,Math.PI*2)
                this.ctx.stroke()
               
            }
            else if(this.currentTool==="ellipse"){
                console.log('i reached here')
                let radiusX = this.ShapeBoundingBox.width/2
                let radiusY = this.ShapeBoundingBox.height/2
                this.ctx.beginPath()
                this.ctx.ellipse(this.MouseDownPos.x,this.MouseDownPos.y,radiusX,radiusY,Math.PI/4,0,Math.PI*2) 
                this.ctx.stroke()
            }else if(this.currentTool==="polygon"){
                this.getPolygon()
                this.ctx.stroke()
            }
        },
        updateRubberbandOnMove(loc){
            this.updateRubberBandSizeData(loc)
            this.drawRubberbandShape(loc)
        },
        AddBrushPoint(x,y,mouseDownPos){
            this.brushXPoints.push(x)
            this.brushYPoints.push(y)
            this.brushDownPos.push(mouseDownPos)
        },
        drawBrush(){
            for(let i=1 ; i<this.brushXPoints.length; i++){
                this.ctx.beginPath()
                if(this.brushDownPos[i]){
                    this.ctx.moveTo(this.brushXPoints[i-1],this.brushYPoints[i-1])
                }else{
                    this.ctx.moveTo(this.brushXPoints[i]-1,this.brushYPoints[i])
                }
                this.ctx.lineTo(this.brushXPoints[i],this.brushYPoints[i])
                this.ctx.closePath()
                this.ctx.stroke()
            }
        },
        ReactToMouseDown(e){
            this.canvas.style.cursor="crosshair"
            this.Location=this.getMousePosition(e.clientX,e.clientY)
            this.saveCanvasImage()
            this.MouseDownPos.x=this.Location.x
            this.MouseDownPos.y=this.Location.y
            this.dragging = true
            if(this.currentTool==='brush'){
                this.usingBrush=true
                this.AddBrushPoint(this.Location.x,this.Location.y)
            }else if(this.currentTool===''){

                    this.Selection(e)
            }
        },
        ReactToMouseMove(e){
            this.canvas.style.cursor="crosshair"
            this.Location=this.getMousePosition(e.clientX,e.clientY)
            if(this.currentTool==='brush'&&this.dragging&&this.usingBrush){
                if(this.Location.x>0&&this.Location.x<this.canvasWidth&& this.Location.y>0&&this.Location.y<this.canvasHeight){
                    this.AddBrushPoint(this.Location.x,this.Location.y,true)
                }
                this.RedrawCanvasImage()
                this.drawBrush()
            }else{
                if(this.dragging){
                    this.RedrawCanvasImage()
                    this.updateRubberbandOnMove(this.Location)
                }
            }
        },
        ReactToMouseUp(e){
            if(this.currentTool != '') this.AddRecentShape()

            this.canvas.style.cursor="default"
            this.Location=this.getMousePosition(e.clientX,e.clientY)
            this.RedrawCanvasImage()
            this.updateRubberbandOnMove(this.Location)
            this.ShapeBoundingBox.width=0
            this.ShapeBoundingBox.height=0
            this.dragging=false
            this.usingBrush=false
        },

        AddRecentShape(){
            console.log(this.ShapeBoundingBox.width)
            console.log(this.ShapeBoundingBox.height)
            if(this.currentTool==="circle" && this.ShapeBoundingBox.width != 0){
                  
                  this.shapes.push(new circle(this.MouseDownPos.x,this.MouseDownPos.y,this.ShapeBoundingBox.width,this.counter++))
            }
            else if(this.currentTool==="square" && this.ShapeBoundingBox.width != 0){
                  
                  this.shapes.push(new square(this.MouseDownPos.x,this.MouseDownPos.y,this.ShapeBoundingBox.width,this.counter++))
            }
            else if(this.currentTool==="rectangle" && this.ShapeBoundingBox.width != 0 &&this.ShapeBoundingBox.height != 0){
                  
                  this.shapes.push(new rectangle(this.MouseDownPos.x,this.MouseDownPos.y,this.ShapeBoundingBox.width,this.ShapeBoundingBox.height,this.counter++))
            }
            
            else if(this.currentTool==="line" && this.ShapeBoundingBox.width != 0 &&this.ShapeBoundingBox.height != 0){
                  
                  this.shapes.push(new line(this.MouseDownPos.x,this.MouseDownPos.y,this.Location.x,this.Location.y,this.counter++))
            }
            else if(this.currentTool==="ellipse" && this.ShapeBoundingBox.width != 0 &&this.ShapeBoundingBox.height != 0){
                  
                  this.shapes.push(new ellipse(this.MouseDownPos.x,this.MouseDownPos.y,this.ShapeBoundingBox.width/2,this.ShapeBoundingBox.height/2,this.counter++))
            }         
            else if(this.currentTool==="polygon" && this.ShapeBoundingBox.width != 0 &&this.ShapeBoundingBox.height != 0){
                  
                  
                  this.shapes.push(new polygon(this.polPoints,this.counter++))
            
            }
           
            
        },
    /*

    onSegment(p, q, r)  
             { 
        if (q.x <= Math.max(p.x, r.x) && 
            q.x >= Math.min(p.x, r.x) && 
            q.y <= Math.max(p.y, r.y) && 
            q.y >= Math.min(p.y, r.y)) 
            { 
            return true; 
             } 
            return false; 
             } ,

        orientation(p,q,r)  
                    { 
             
            let val = (q.y - p.y) * (r.x - q.x) 
                    - (q.x - p.x) * (r.y - q.y); 
    
            if (val == 0)  
            { 
                return 0; 
            } 
            return (val > 0) ? 1 : 2;  
            } ,

         doIntersect(p1,q1,p2,q2)  {

        let o1 = this.orientation(p1, q1, p2) 
        let o2 = this.orientation(p1, q1, q2) 
        let o3 = this.orientation(p2, q2, p1) 
        let o4 = this.orientation(p2, q2, q1)
  
        if (o1 != o2 && o3 != o4) 
        {   
            console.log(true)
            return true; 
        } 
  
        if (o1 == 0 &&this.onSegment(p1, p2, q1))  
        { console.log(true)
            return true; 
        } 

        if (o2 == 0 && this.onSegment(p1, q2, q1))  
        { console.log(true)
            return true; 
        } 
  
        if (o3 == 0 && this.onSegment(p2, p1, q2)) 
        { console.log(true)
            return true; 
        } 
        if (o4 == 0 && this.onSegment(p2, q1, q2)) 
        { console.log(true)
            return true; 
        } console.log(false)
        return false;  
          } ,


     isInside(polygon,n,p)   { 
        
        if (n < 3)  
        { 
            return false; 
        } 
        let extreme = new polygonPoint(Infinity, p.y); 
  
        let count = 0, i = 0; 
        do 
        { 
            let next = (i + 1) % n; 
  

            if (this.doIntersect(polygon[i], polygon[next], p, extreme))  
            { 

                if (this.orientation(polygon[i], p, polygon[next]) == 0) 
                { 
                    return this.onSegment(polygon[i], p, 
                                     polygon[next]); 
                } 
  
                count++; 
            } 
            i = next; 
        } while (i != 0); 
  
        
        return (count % 2 == 1); 
    } , */
  
        
        Selection(){
       
           console.log(this.shapes.length)
           
           let i = this.shapes.length-1
             for( i ; i >=0 ; i--){               
                let currShape=this.shapes[i]
                 
                 if(currShape instanceof circle){ //Circle Detection
                
                 let distance = (Math.sqrt(Math.pow((this.MouseDownPos.x-currShape.x),2)+Math.pow((this.MouseDownPos.y-currShape.y),2)))

                 if(distance<=currShape.r){
                      console.log("iam aCircle of id" + currShape.id)
                  }
                  break
                  }
                else if(currShape instanceof square) { // Square Detection
                      if(this.MouseDownPos.x >= currShape.x && this.MouseDownPos.x <= currShape.x+currShape.length && this.MouseDownPos.y >= currShape.y && this.MouseDownPos.y <= currShape.y+currShape.length){
                          console.log("iam a square of id" + currShape.id)
                      }
                    break
                  }
                else if(currShape instanceof rectangle) { // Square Detection
                      if(this.MouseDownPos.x >= currShape.x && this.MouseDownPos.x <= currShape.x+currShape.width && this.MouseDownPos.y >= currShape.y && this.MouseDownPos.y <= currShape.y+currShape.height){
                          console.log("iam a rectangle of id" + currShape.id)
                      }
                      break
                  }
                else if(currShape instanceof line) { // line Detection
                        var slope = (currShape.p2y-currShape.p1y)/(currShape.p2x-currShape.p1x)
                        console.log(this.MouseDownPos.y-currShape.p1y-slope*(this.MouseDownPos.x-currShape.p1x))
                        console.log(this.MouseDownPos.y-currShape.p1y-slope*(this.MouseDownPos.x-currShape.p1x ))
                        
                        if(this.MouseDownPos.y-currShape.p1y - slope*(this.MouseDownPos.x-currShape.p1x )<= 5.0 ){
                            console.log("iam a Line of id" + currShape.id)
                        }
                        break
                  }
                 else if(currShape instanceof ellipse) { // ellipse Detection

                    console.log(Math.pow((this.MouseDownPos.x-currShape.x)/currShape.r1,2)+Math.pow((this.MouseDownPos.y-currShape.y)/currShape.r2,2))
                      if(Math.pow((this.MouseDownPos.x-currShape.x)/currShape.r1,2)+Math.pow((this.MouseDownPos.y-currShape.y)/currShape.r2,2)<=1)
                          console.log("iam a ellipse of id" + currShape.id)
                          break
                    }
                /* else if(currShape instanceof polygon) { // polygon Detection
                    
                    let currClick=new polygonPoint(this.MouseDownPos.x,this.MouseDownPos.y)
                    console.log(currShape.polpoints)
                    if(this.isInside(currShape.polpoints,6,currClick)){

                            console.log("iamPol"+currShape.id)
                    }  
   

                    }*/  
                    
             
                  
            }

        }


    }
}
</script>
<style >
#my-canvas{
    margin: auto;
    border :3px solid #000000;
}
</style>