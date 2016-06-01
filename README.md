# NeteaseNewsTabAnimation

###Outline
参考 http://blog.csdn.net/tyk0910/article/details/51460808#rd 这篇文章实现

* 从一个recyclerview将item移动到另一个recyclerview，带动画效果

* 对一个recyclerview的元素拖动改变位置和平滑删除

###Points
#####1. item跨rv移动
* 利用removeview和addview方法将一个item元素从rv移动到父view，开启动画实现移动效果，移动到目标位置后addview到另一个rv中

* addview如果只指定宽高属性，调用addView(View child, LayoutParams params)则
  
  * 对于relativelayout，默认坐标位置是getLeft = 0, getTop = 0

  * 对于LinearLayout，不指定_orientation_属性下，默认横向放置元素到末尾，_orientation_指定为vertical时竖直放置到末尾，所以如果想实现item动画
  效果，使用LinearLayout是不行的
  
  * 对于一个有父布局的view，如果不执行removeview直接addview到另一个布局会编译出错
  
* PathMeasure可以用来计算路径距离，通过路径中间值获取路径坐标，在ValueAnimator中调用并使用setTranslationX/Y方法对view进行位置移动（但其实
__坐标是没有改变的__）

* 在adapter的onBindViewHolder中对于每一个view添加click监听，在onclick事件中调用onAllTabsListener接口的allTabsItemClick方法，activity实现
onAllTabsListner接口的allTabsItemClick方法，并将自己传给adapter

#####2. item拖动和滑动删除

* 定义一个继承ItemTouchHelper.CallBack的Callback，getMovementFlags设置手势类型，onMove中实现拖动后交换view的逻辑，onSwiped实现滑动删除逻辑

* adapter实现抽象接口onMoveAndSwipeListener，callback类通过接口将手势事件的view position信息传给adapter

* adapter通过Collections.swap(fromPosition, toPosition), notifyItemMoved(fromPosition, toPosition),notifyItemRemoved(position)等方法
实现
