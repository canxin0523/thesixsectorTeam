<template>
  <a-layout id="homeLayout" :style="{ overflow: 'auto', height: '100vh' }">
    <a-layout-sider :trigger="null" collapsible v-model="collapsed">
      <a-menu theme="dark" mode="inline">
        <a-menu-item>首页</a-menu-item>
        <a-sub-menu v-for="(itParent,index) in NavData" :key="index" :title="itParent.title">
          <template v-if="itParent.child">
            <a-menu-item v-for="itChild in itParent.child" :key="itChild.navId">{{itChild.title}}</a-menu-item>
          </template>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header id="hell_head">
        <a-icon
          style=" margin-left: 15px;font-size: 1.2rem;vertical-align: middle;"
          :type="collapsed ? 'menu-unfold' : 'menu-fold'"
          @click="()=> collapsed = !collapsed"
        />
      </a-layout-header>
      <a-layout-content id="hell_content">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script>
export default {
  name: "home",
  data() {
    return {
      collapsed: false,
      NavData: [
        // 菜单列表，待修改
        {
          navId: 100,
          icons: "dashboard",
          title: "生死簿",
          path: "",
          child: [
            {
              navId: 101,
              icons: "",
              title: "用户管理",
              path: "/userInfo"
            },
            {
              navId: 102,
              icons: "",
              title: "数据同步",
              path: "/dataSynchroinsm"
            }
          ]
        },
        {
          navId: 200,
          icons: "audit",
          title: "勾魂管理",
          path: "",
          child: []
        },
        {
          navId: 300,
          icons: "audit",
          title: "十八层地狱",
          path: "",
          child: [
            {
              navId: 301,
              icons: "",
              title: "设备管理",
              path: "/equipmentManage"
            },
            {
              navId: 302,
              icons: "",
              title: "作业流程",
              path: "/process"
            },
            {
              navId: 303,
              icons: "",
              title: "用户管理",
              path: "/hellUserManage"
            }
          ]
        }
      ],
      currentSelectChild: "",
      currentParent: []
    };
  },
  methods: {}
};
</script>
<style lang="less">
@import "../../assets/css/common.less";
</style>
<style lang="less" scoped>
#homeLayout {
  #hell_head {
    background: #fff;
    padding: 0;
    height: 7vh;
  }

  #hell_content {
    margin: 1.5vh 10px;
    padding: 10px;
    background: #fff;
    height: 90vh;
  }
}
</style>