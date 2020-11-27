<template>
<div>yala bena</div>
 <canvas id ="my-canvas" width="600" height="600"></canvas>   
</template>

<script>
import {polygonPoint} from './classes.js'

export default {
    name:'paintp',
    
    data() {
        return {
           
            rectangles :[],
            circles :[],
            ellipses:[],
            polygons:[],
            currentTool:'circle',
            canvas:'',
            ctx:'',
            savedImageData:'',
            strokeColor:'black',
            line_Width:2,
            fillColor:'black',
            canvasWidth:600,
            canvasHeight:600,
            polygonSides:4,
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
            console.log(polygonPoints)
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
            }else if(this.currentTool==="circle"){
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
            this.canvas.style.cursor="default"
            this.Location=this.getMousePosition(e.clientX,e.clientY)
            this.RedrawCanvasImage()
            this.updateRubberbandOnMove(this.Location)
            this.dragging=false
            this.usingBrush=false
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