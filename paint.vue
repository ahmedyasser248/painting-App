 
<template>
<div id="toolbar">
<label id="title">PaintApp</label>
<div id="btn-grp">

<button class="btn" @click="ChangeTool('triangle')">triangle</button>
<button class="btn" @click="ChangeTool('square')" type="button">square</button>
<button class="btn" @click="ChangeTool('circle')" type="button">circle</button>
<button class="btn" @click="ChangeTool('ellipse')" type="button">Ellipse</button>
 <button class="btn" @click="ChangeTool('polygon')" type="button">polygon</button>
 <button class="btn" @click="ChangeTool('rectangle')" type="button">rectangle</button>
 <button class="btn" @click="ChangeTool('line')" type="button">line</button>
 <button class="btn" @click="ChangeTool('moving')" type="button">Drag</button>
 <button class="btn" @click="ChangeTool('resize')" type="button">Resize</button>
 <button class="btn" @click="ChangeTool('delete')" type="button">Delete</button>
 <input id="color" type="color" class="btn" value="#33EAFF">
 <input id ="line_width" type="number" name="lineWidth" list="linewidth" placeholder="Line width"/>
 <datalist id="linewidth" >
 <option value="12"></option>
 <option value="14"></option>
 <option value="18"></option>
 <option value="24"></option>
 <option value="40"></option>
 </datalist>
</div>
</div>
 <canvas id ="my-canvas" width="1400" height="800"></canvas>   
</template>


<script>
import {polygonPoint,square,circle,rectangle, line,ellipse,polygon} from './classes.js'
export default {
    name:'paintp',
    
    data() {
        return {
            changeWidth:false,
            changeColor:false,
            first:false,
            currShape:null,
            dragButton:false,
            resizeButton:false,
            inf:1000,
            counter:0,
            shapes :[],
            polPoints:[],
            recentShape:'',
            currentTool:'ellipse',
            canvas:'',
            ctx:'',
            savedImageData:'',
            strokeColor:"black",
            line_Width:2,
            fillColor:'white',
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
        $vm.ctx.lineWidth=$vm.line_Width
        $vm.canvas.addEventListener("mousedown",$vm.ReactToMouseDown)
        $vm.canvas.addEventListener("mousemove",$vm.ReactToMouseMove)
        $vm.canvas.addEventListener("mouseup",$vm.ReactToMouseUp)
        let colorInput=document.querySelector('#color') 
        colorInput.addEventListener('input',()=>{
          $vm.strokeColor=colorInput.value
          $vm.ctx.strokeColor=$vm.strokeColor
          $vm.changeColor=true
          console.log($vm.strokeColor)
        })
        let lineWinput=document.querySelector('#line_width')
        lineWinput.addEventListener('input',()=>{
            $vm.line_Width=lineWinput.value
            $vm.ctx.lineWidth=$vm.line_Width
            $vm.changeWidth=true
            console.log($vm.ctx.lineWidth)
        })
    },
    methods:{
        ChangeTool(toolClicked){
            this.currentTool=toolClicked
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
         gettrianglepoints(){
            let trianglePoint=[]
            var p1=new polygonPoint(this.MouseDownPos.x,this.MouseDownPos.y)
            var p2=new polygonPoint(this.Location.x,this.Location.y)
            var distancex=this.MouseDownPos.x-this.Location.x
            var distancey=this.MouseDownPos.y-this.Location.y
            var p3=new polygonPoint(this.Location.x-distancex,this.Location.y+distancey)
            trianglePoint.push(p1)
            trianglePoint.push(p2)
            trianglePoint.push(p3)
            return trianglePoint
        },
        getTriangle(){
            let trianglepoint=this.gettrianglepoints()
            this.ctx.beginPath()
            this.ctx.moveTo(trianglepoint[0].x,trianglepoint[0].y)
            this.ctx.lineTo(trianglepoint[1].x,trianglepoint[1].y)
            this.ctx.lineTo(trianglepoint[2].x,trianglepoint[2].y)
            this.ctx.closePath()
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
            this.ctx.lineWidth=this.line_Width
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
                
                let radiusX = this.ShapeBoundingBox.width/2
                let radiusY = this.ShapeBoundingBox.height/2
                this.ctx.beginPath()
                this.ctx.ellipse(this.MouseDownPos.x,this.MouseDownPos.y,radiusX,radiusY,Math.PI/4,0,Math.PI*2) 
                this.ctx.stroke()
                
            }else if(this.currentTool==="polygon"){
                this.getPolygon()
                this.ctx.stroke()
                
            }else if(this.currentTool==="triangle"){
                    this.getTriangle()
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
        drawDrag(object){
            this.ctx.lineWidth=2
            this.ctx.strokeStyle="black"
                if(object instanceof circle){
                    object.x=this.Location.x
                    object.y=this.Location.y
                    let radius =object.r
                    this.ctx.beginPath()
                    this.ctx.arc(object.x,object.y,radius,0,Math.PI*2)
                    this.ctx.stroke()
                      
                }else if(object instanceof square){
                    object.x=this.Location.x
                    object.y=this.Location.y
                    this.ctx.strokeRect(object.x,object.y,object.length,object.length)
                    
                }else if(object instanceof rectangle){
                    object.x=this.Location.x
                    object.y=this.Location.y
                    this.ctx.strokeRect(object.x,object.y,object.width,object.height)
                    
                }else if(object instanceof ellipse){
                    object.x=this.Location.x
                    object.y=this.Location.y
                    let radiusX = object.r1
                    let radiusY = object.r2
                    this.ctx.beginPath()
                    this.ctx.ellipse(object.x,object.y,radiusX,radiusY,Math.PI/4,0,Math.PI*2) 
                    this.ctx.stroke()
                    
                }else if(object instanceof polygon){
                    let m = this.Location.x - object.polpoints[0].x
                    let n = this.Location.y - object.polpoints[0].y
                    for(let k=0;k<this.polygonSides;k++){
                        object.polpoints[k].x +=m
                        object.polpoints[k].y +=n
                    }
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.polpoints[0].x,object.polpoints[0].y)
                    for(let i=1;i<this.polygonSides; i++){
                        this.ctx.lineTo(object.polpoints[i].x,object.polpoints[i].y)
                    }
                    this.ctx.closePath()
                    this.ctx.stroke()
                }else if(object instanceof line){
                    let x = this.Location.x - object.p1x
                    let y = this.Location.y - object.p1y
                    object.p1x+=x
                    object.p1y+=y
                    object.p2x+=x
                    object.p2y+=y
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.p1x,object.p1y)
                    this.ctx.lineTo(object.p2x,object.p2y)
                    this.ctx.stroke()
                }
            
        },
        drawResize(object){
            this.ctx.lineWidth=2
            this.ctx.fillStyle=this.fillColor
            this.ctx.strokeStyle="black"
                if(object instanceof circle){
                    this.updateRubberBandSizeData(this.Location)
                    let diff = this.ShapeBoundingBox.width
                    object.r =diff
                    this.ctx.beginPath()
                    this.ctx.arc(object.x,object.y,object.r,0,Math.PI*2)
                    this.ctx.stroke() 
                }else if(object instanceof square){
                    this.updateRubberBandSizeData(this.Location)
                    object.length =this.ShapeBoundingBox.width
                    this.ctx.strokeRect(object.x,object.y,object.length,object.length)
                }else if(object instanceof rectangle){
                    this.updateRubberBandSizeData(this.Location)
                    object.width =this.ShapeBoundingBox.width
                    object.height=this.ShapeBoundingBox.height
                    this.ctx.strokeRect(object.x,object.y,object.width,object.height)
                }else if(object instanceof ellipse){
                    this.updateRubberBandSizeData(this.Location)
                    object.r1=this.ShapeBoundingBox.width/2
                    object.r2=this.ShapeBoundingBox.height/2
                    this.ctx.beginPath()
                    this.ctx.ellipse(object.x,object.y,object.r1,object.r2,Math.PI/4,0,Math.PI*2) 
                    this.ctx.stroke()
                }else if(object instanceof polygon){
                    this.updateRubberBandSizeData(this.Location)
                    let radiusX = this.ShapeBoundingBox.width
                    let radiusY = this.ShapeBoundingBox.height
                    let angle = 0
                    for(let i = 1; i < this.polygonSides; i++){
                            var pp=new polygonPoint(this.ShapeBoundingBox.top+radiusX*Math.sin(angle),this.ShapeBoundingBox.left-radiusY*Math.cos(angle))
                            object.polpoints[i]=pp
                            angle=angle+2*Math.PI/this.polygonSides
                    }
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.polpoints[0].x,object.polpoints[0].y)
                    for(let i=1;i<this.polygonSides; i++){
                        this.ctx.lineTo(object.polpoints[i].x,object.polpoints[i].y)
                    }
                    this.ctx.closePath()
                    this.ctx.stroke()
                }else if(object instanceof line){
                    let x = this.Location.x - object.p2x
                    let y = this.Location.y - object.p2y
                    object.p2x+=x
                    object.p2y+=y
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.p1x,object.p1y)
                    this.ctx.lineTo(object.p2x,object.p2y)
                    this.ctx.stroke()
                }
            
        },
        prevDraw(object){
            if(object!=null){
            this.ctx.strokeStyle="black"
                for(let i=0;i<this.shapes.length;i++){
                    if(this.shapes[i].id!=object.id){
                        if(this.shapes[i] instanceof circle){
                            let radius =this.shapes[i].r
                            this.ctx.beginPath()
                            this.ctx.arc(this.shapes[i].x,this.shapes[i].y,radius,0,Math.PI*2)
                            this.ctx.stroke()
                      
                        }else if(this.shapes[i] instanceof square){
                            this.ctx.strokeRect(this.shapes[i].x,this.shapes[i].y,this.shapes[i].length,this.shapes[i].length)
                        }else if(this.shapes[i] instanceof rectangle){
                            this.ctx.strokeRect(this.shapes[i].x,this.shapes[i].y,this.shapes[i].width,this.shapes[i].height)   
                        }else if(this.shapes[i] instanceof ellipse){
                            let radiusX = this.shapes[i].r1
                            let radiusY = this.shapes[i].r2
                            this.ctx.beginPath()
                            this.ctx.ellipse(this.shapes[i].x,this.shapes[i].y,radiusX,radiusY,Math.PI/4,0,Math.PI*2) 
                            this.ctx.stroke()
                            
                        }else if(this.shapes[i] instanceof polygon){
                            this.ctx.beginPath()
                            this.ctx.moveTo(this.shapes[i].polpoints[0].x,this.shapes[i].polpoints[0].y)
                            for(let j=1;j<this.polygonSides; j++){
                                this.ctx.lineTo(this.shapes[i].polpoints[j].x,this.shapes[i].polpoints[j].y)
                            }
                            this.ctx.closePath()
                            this.ctx.stroke()
                        }else if(this.shapes[i] instanceof line){
                            this.ctx.beginPath()
                            this.ctx.moveTo(this.shapes[i].p1x,this.shapes[i].p1y)
                            this.ctx.lineTo(this.shapes[i].p2x,this.shapes[i].p2y)
                            this.ctx.stroke()
                        }
                    }
                }
        }
        },
        clearSelected(object){
                this.ctx.strokeStyle="white"
                this.ctx.lineWidth=5
                if(object instanceof circle){
                    let radius =object.r
                    this.ctx.beginPath()
                    this.ctx.arc(object.x,object.y,radius,0,Math.PI*2)
                    this.ctx.stroke()                   
                    this.ctx.closePath()                                                                                                                                   
                }else if(object instanceof square){
                    this.ctx.strokeRect(object.x,object.y,object.length,object.length)
                    
                }else if(object instanceof rectangle){
                    this.ctx.strokeRect(object.x,object.y,object.width,object.height)
                    
                }else if(object instanceof ellipse){
                    let radiusX = object.r1
                    let radiusY = object.r2
                    this.ctx.beginPath()
                    this.ctx.ellipse(object.x,object.y,radiusX,radiusY,Math.PI/4,0,Math.PI*2) 
                    this.ctx.stroke()
                    
                }else if(object instanceof polygon){
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.polpoints[0].x,object.polpoints[0].y)
                    for(let i=1;i<this.polygonSides; i++){
                        this.ctx.lineTo(object.polpoints[i].x,object.polpoints[i].y)  
                    }
                    this.ctx.closePath()
                    this.ctx.stroke()
                }else if(object instanceof line){
                    this.ctx.beginPath()
                    this.ctx.moveTo(object.p1x,object.p1y)
                    this.ctx.lineTo(object.p2x,object.p2y)
                    this.ctx.stroke()
                }
                this.ctx.lineWidth=2
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
            }
            else if(this.currentTool==='moving'){
                    this.dragButton = true
                    this.Selection(e)
                    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height)
                    this.clearSelected(this.currShape)
                    this.saveCanvasImage()
                    this.prevDraw(this.currShape)
                    this.saveCanvasImage()
            }else if(this.currentTool==='resize'){
                    this.resizeButton = true
                    this.Selection(e)
                    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height)
                    this.clearSelected(this.currShape)
                    this.saveCanvasImage()
                    this.prevDraw(this.currShape)
                    this.saveCanvasImage()
            }else if(this.currentTool==='delete'){
                    this.Selection(e)
                    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height)
                    this.clearSelected(this.currShape)
                    this.saveCanvasImage()
                    this.prevDraw(this.currShape)
                    this.saveCanvasImage()
                    for(let i=0;i<this.shapes.length;i++){
                        if(this.shapes[i].id===this.currShape.id){
                            this.shapes.splice(i,1)
                        }
                    }
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
                    if(this.dragButton){
                        this.drawDrag(this.currShape)
                    }else if(this.resizeButton){
                        this.drawResize(this.currShape)
                    }
                    else{
                    this.updateRubberbandOnMove(this.Location)}
                }
            }
        },
        ReactToMouseUp(e){
            if(!this.changeColor&&!this.changeWidth){
            if(this.currentTool != '' &&this.currentTool != 'moving' && this.currentTool != 'resize') this.AddRecentShape()
            this.canvas.style.cursor="default"
            this.Location=this.getMousePosition(e.clientX,e.clientY)
            this.RedrawCanvasImage()
            if(this.dragButton){
                    this.drawDrag(this.currShape)
            }else if(this.resizeButton){
                       this.drawResize(this.currShape) 
            }
            else{
                    this.updateRubberbandOnMove(this.Location)
            }
            this.ShapeBoundingBox.width=0
            this.ShapeBoundingBox.height=0
            this.dragging=false
            this.dragButton=false
            this.resizeButton=false
            this.usingBrush=false
            this.currShape=null
            console.log(this.shapes)}
            else{
                this.changeColor=false
                this.changeWidth=false
            }
        },
        AddRecentShape(){
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
           
            return true; 
        } 
  
        if (o1 == 0 &&this.onSegment(p1, p2, q1))  
        { 
            return true; 
        } 
        if (o2 == 0 && this.onSegment(p1, q2, q1))  
        {
            return true; 
        } 
  
        if (o3 == 0 && this.onSegment(p2, p1, q2)) 
        { 
            return true; 
        } 
        if (o4 == 0 && this.onSegment(p2, q1, q2)) 
        { 
            return true; 
        } 
        return false;  
          } ,
     isInside(polygon,n,p)   { 
        
        if (n < 3)  
        { 
            return false; 
        } 
        let extreme = new polygonPoint(this.inf, p.y); 
  
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
    } , 
  
        
        Selection(){
       
           
           
            let i = this.shapes.length-1
             for( i; i >=0 ; i--){  
                 this.currShape=this.shapes[i]
                 
                 if(this.currShape instanceof circle){ //Circle Detection
                
                 let distance = (Math.sqrt(Math.pow((this.MouseDownPos.x-this.currShape.x),2)+Math.pow((this.MouseDownPos.y-this.currShape.y),2)))
                 if(distance<=this.currShape.r){
                      console.log("iam aCircle of id" + this.currShape.id)
                      break
                  }else{
                      this.currShape=null
                  }
                  
                  }
                else if(this.currShape instanceof square) { // Square Detection
                      if(this.MouseDownPos.x >= this.currShape.x && this.MouseDownPos.x <= this.currShape.x+this.currShape.length && this.MouseDownPos.y >= this.currShape.y && this.MouseDownPos.y <= this.currShape.y+this.currShape.length){
                          console.log("iam a square of id" + this.currShape.id)
                          break
                      }else{
                      this.currShape=null
                  }
                    
                  }
                else if(this.currShape instanceof rectangle) { // Square Detection
                      if(this.MouseDownPos.x >= this.currShape.x && this.MouseDownPos.x <= this.currShape.x+this.currShape.width && this.MouseDownPos.y >= this.currShape.y && this.MouseDownPos.y <= this.currShape.y+this.currShape.height){
                          console.log("iam a rectangle of id" + this.currShape.id)
                          break
                      }else{
                      this.currShape=null
                  }
                      
                  }
                else if(this.currShape instanceof line) { // line Detection
                        var slope = (this.currShape.p2y-this.currShape.p1y)/(this.currShape.p2x-this.currShape.p1x)
                        
                        if(Math.abs(this.MouseDownPos.y-this.currShape.p1y - slope*(this.MouseDownPos.x-this.currShape.p1x ))<= 5.0 ){
                            console.log("iam a Line of id" + this.currShape.id)
                            break
                        }else{
                      this.currShape=null
                  }
                        
                  }
                 else if(this.currShape instanceof ellipse) { // ellipse Detection
                    if(this.currShape.r1>this.currShape.r2){
                      if( (Math.pow(((this.MouseDownPos.x-this.currShape.x)/(this.currShape.r1)/2),2)+Math.pow(((this.MouseDownPos.y-this.currShape.y)/(this.currShape.r1/2)),2)) <= 1){
                          console.log("iam a ellipse of id" + this.currShape.id)
                          break
                      }
                      }else{
                         
                        if(Math.pow(((this.MouseDownPos.x-this.currShape.x)/this.currShape.r2),2)+Math.pow(((this.MouseDownPos.y-this.currShape.y)/this.currShape.r2),2)<=1){
                          console.log("iam a ellipse of id" + this.currShape.id)
                          break
                      }
                      }
                    }
                 else if(this.currShape instanceof polygon) { // polygon Detection
                    
                    let currClick=new polygonPoint(this.MouseDownPos.x,this.MouseDownPos.y)
                    
                    if(this.isInside(this.currShape.polpoints,6,currClick)){
                            console.log("iamPol"+this.currShape.id)
                            break
                    }  
   
                    }else{
                      this.currShape=null
                  }
                    
             
                  
            }
        }
    }
}
</script>
<style >
#title{
 margin: 1em 0 0.5em 0;
	color: #343434;
	font-weight: normal;
	font-family: 'Ultra', sans-serif;   
	font-size: 36px;
	line-height: 42px;
	text-transform: uppercase;
	text-shadow: 0 2px white, 0 3px #777;
}
#btn-grp{
  display:flex;
  justify-content: space-around;

  
}
.btn{
 position: relative;
  background-color: #020202;
  border: none;
  font-size: 18px;
  color: #63e2d8;
  padding: 20px;
  width: 200px;
  text-align: center;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  overflow: hidden;
  cursor: pointer;

}
.btn:after {
  content: "";
  background: #21f1d6;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -20px!important;
  margin-top: -120%;
  opacity: 0;
  transition: all 0.8s
}

.btn:active:after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}
#toolbar{
  height: 100px;
  background-color: rgb(73, 210, 219);
  margin-bottom: 20px;
}
#my-canvas{

    background-color: white;
    margin-top: 10px;
    margin-right: auto;
    margin-left: auto;
    border :3px solid #000000;
    display: block;
}
#line_width{
    border :2px solid  rgb(73, 210, 219);
    padding: 12px;
    width: 100px;
}
</style>
