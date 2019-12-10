<template>
  <div id="login">
    <div class="loginBox">
      <div class="loginTitle">地府管理系统</div>
      <div class="tabs">
        <a-tabs>
          <a-tab-pane tab="账号登录" key="1"></a-tab-pane>
          <a-tab-pane tab="手机登录" key="2"></a-tab-pane>
          <a-tab-pane tab="扫码登录" key="3"></a-tab-pane>
        </a-tabs>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Login'
}
</script>
<style lang="less">
@import "../../assets/css/common.less";
@import "../../assets/css/login.less";
</style>