<template>
  <a-layout id="home-layout" style="min-height: 100vh">
    <a-layout-sider>
      <div class="logo">地府管理系统</div>
      <a-menu theme="dark" :defaultSelectedKeys="['1']" mode="inline">
        <a-sub-menu
          v-for="(item,index) in NavData"
          :key="item.navId"
          :class="{'disapper-arrow': (item.child.length == 0)}"
        >
          <div slot="title" class="title">
            <img :src="'../../../static/images/home/home-icon'+ (index + 1) +'.png'" alt />
            <span>{{item.title}}</span>
          </div>
          <a-menu-item
            v-show="item.child.length"
            v-for="child in item.child"
            :key="child.navId"
          >{{child.title}}</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0" />
      <a-layout-content style="margin: 0 16px">
        <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">Bill is a cat.</div>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
<script>
export default {
  name: "home",
  data() {
    return {
      NavData: [
        // 菜单列表，待修改
        {
          navId: 100,
          title: "首页",
          child: []
        },
        {
          navId: 200,
          title: "生死簿",
          child: [
            {
              navId: 201,
              title: "用户管理"
            },
            {
              navId: 202,
              title: "数据同步"
            }
          ]
        },
        {
          navId: 300,
          title: "勾魂管理",
          child: []
        }
      ]
    };
  },
  methods: {}
};
</script>
<style lang="less">
@import "../../assets/css/common.less";
</style>
<style lang="less" scoped>
#home-layout {
  .ant-layout-sider {
    flex: none !important;
    width: 180px !important;
    min-width: 180px !important;
    max-width: 180px !important;
    .ant-layout-sider-children {
      .logo {
        height: 48px;
        line-height: 48px;
        font-size: 14px;
        color: #fff;
        padding-left: 15px;
        font-weight: 700;
      }
      .ant-menu {
        li {
          min-height: 50px;
          /deep/ .ant-menu-submenu-title {
            padding-left: 15px !important;
            .title {
              img {
                width: 15px;
                height: 15px;
              }
              span {
                font-size: 14px;
                color: #fff;
                vertical-align: middle;
              }
            }
          }

          &.disapper-arrow {
            /deep/ .ant-menu-submenu-title {
              .ant-menu-submenu-arrow {
                &::before {
                  width: 0;
                }
                &::after {
                  width: 0;
                }
              }
            }
          }
        }
      }
    }
  }
  .ant-layout {
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
}
</style>