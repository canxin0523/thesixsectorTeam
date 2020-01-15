<template>
  <div :class="[multipage === true ? 'multi-page':'single-page', 'not-menu-page', 'home-page']">
    <a-row :gutter="8" class="head-info">
      <a-card class="head-info-card">
        <a-col :span="12">
          <div class="head-info-avatar">
            <img alt="头像" :src="avatar">
          </div>
          <div class="head-info-count">
            <div class="head-info-welcome">
              {{welcomeMessage}}
            </div>
            <div class="head-info-desc">
              <p>{{user.deptName ? user.deptName : '暂无部门'}} | {{user.roleName ? user.roleName : '暂无角色'}}</p>
            </div>
            <div class="head-info-time">上次登录时间：{{user.lastLoginTime ? user.lastLoginTime : '第一次访问系统'}}</div>
          </div>
        </a-col>
        <a-col :span="12">
          <div>
            <a-row class="more-info">
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4"></a-col>
              <a-col :span="4">
                <head-info title="今日IP" :content="todayIp" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="今日访问" :content="todayVisitCount" :center="false" :bordered="false"/>
              </a-col>
              <a-col :span="4">
                <head-info title="总访问量" :content="totalVisitCount" :center="false" />
              </a-col>
            </a-row>
          </div>
        </a-col>
      </a-card>
    </a-row>
    <a-row :gutter="8" class="count-info">
      <a-col :span="12" class="visit-count-wrapper">
        <a-card class="visit-count">
          <apexchart ref="count" type=bar height=300 :options="chartOptions" :series="series" />
        </a-card>
      </a-col>
      <a-col :span="12" class="project-wrapper">
        <a-card title="进行中的项目" class="project-card">
          <a href="https://github.com" target="_blank" slot="extra">所有项目</a>
          <table>
            <tr>
              <td>
                <div class="project-avatar-wrapper">
                  <a-avatar class="project-avatar">{{projects[0].avatar}}</a-avatar>
                </div>
                <div class="project-detail">
                  <div class="project-name">
                    {{projects[0].name}}
                  </div>
                  <div class="project-desc">
                    <p>{{projects[0].des}}</p>
                  </div>
                </div>
              </td>
              <td>
                <div class="project-avatar-wrapper">
                  <a-avatar class="project-avatar">{{projects[1].avatar}}</a-avatar>
                </div>
                <div class="project-detail">
                  <div class="project-name">
                    {{projects[1].name}}
                  </div>
                  <div class="project-desc">
                    <p>{{projects[1].des}}</p>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="project-avatar-wrapper">
                  <a-avatar class="project-avatar">{{projects[2].avatar}}</a-avatar>
                </div>
                <div class="project-detail">
                  <div class="project-name">
                    {{projects[2].name}}
                  </div>
                  <div class="project-desc">
                    <p>{{projects[2].des}}</p>
                  </div>
                </div>
              </td>
              <td>
                <div class="project-avatar-wrapper">
                  <a-avatar class="project-avatar">{{projects[3].avatar}}</a-avatar>
                </div>
                <div class="project-detail">
                  <div class="project-name">
                    {{projects[3].name}}
                  </div>
                  <div class="project-desc">
                    <p>{{projects[3].des}}</p>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="project-avatar-wrapper">
                  <a-avatar class="project-avatar">{{projects[4].avatar}}</a-avatar>
                </div>
                <div class="project-detail">
                  <div class="project-name">
                    {{projects[4].name}}
                  </div>
                  <div class="project-desc">
                    <p>{{projects[4].des}}</p>
                  </div>
                </div>
              </td>
              <td></td>
            </tr>
          </table>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>
<script>
  /* eslint-disable */
  import HeadInfo from '@/views/common/HeadInfo'
  import {mapState} from 'vuex'
  import moment from 'moment'
  moment.locale('zh-cn')

  export default {
    name: 'HomePage',
    components: {HeadInfo},
    data () {
      return {
        series: [],
        chartOptions: {
          chart: {
            toolbar: {
              show: false
            }
          },
          plotOptions: {
            bar: {
              horizontal: false,
              columnWidth: '35%'
            }
          },
          dataLabels: {
            enabled: false
          },
          stroke: {
            show: true,
            width: 2,
            colors: ['transparent']
          },
          xaxis: {
            categories: []
          },
          fill: {
            opacity: 1

          }
        },
        projects: [
          {
            name: 'Sixsector-Naturelaw',
            des: '天道管理系统。',
            avatar: 'T'
          },
          {
            name: 'Sixsector-Asura',
            des: '阿修罗道管理系统。',
            avatar: 'A'
          },
          {
            name: 'Sixsector-Humanity',
            des: '人道管理系统。',
            avatar: 'R'
          },
          {
            name: 'Sixsector-Beasts',
            des: '畜生道管理系统。',
            avatar: 'C'
          },
          {
            name: 'Sixsector-Hungry',
            des: '饿鬼道管理系统。',
            avatar: 'E'
          }
        ],
        todayIp: '',
        todayVisitCount: '',
        totalVisitCount: '',
        userRole: '',
        userDept: '',
        lastLoginTime: '',
        welcomeMessage: ''
      }
    },
    computed: {
      ...mapState({
        multipage: state => state.setting.multipage,
        user: state => state.account.user
      }),
      avatar () {
        return `static/avatar/${this.user.avatar}`
      }
    },
    methods: {
      welcome () {
        const date = new Date()
        const hour = date.getHours()
        let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')))
        let welcomeArr = [
          '你若多来一时，我便多欢喜一时',
          '孟婆汤八泪为引，去其苦涩，留其甘芳，如此煎熬一生，方熬成一锅好汤。',
          '是否我多像她几分，你便能多喜欢我几分',
          '会不会有一天，你会带我去看山花烂漫，会不会有一天你会带我行过人间的万里河山？',
          '你说，我的眼睛像极了你见过的一个人，那个女人好可怕，他着急要吃你，但是，她的眼睛很好看，原谅我，不是她像我，而是我像她，原来你喜欢的一直是她。',
          '你莫忘了我，我在黄泉陪你长生。',
          '你若是想去人间，我便可与你一起，去看花开花落，游遍万里河山。',
          '你若是嫌此地荒芜，我可将曼殊沙华植满这黄泉，你每日起身推窗，便可看见八百里花海，延绵留恋，天上人间，唯你一处。',
          '彼有死境，魂之归路。足八百里，无花无叶。黄沙遍地，延绵流潋，故名：黄泉',
          '一千年开花，一千年落花，花叶永不见。',
          '佼佼佳人，江东之畔。',
          '风之萧萧，雨之寥寥。',
          '思之不见，佳人不还。',
          '江东之畔，埋吾相思。',
          '情之所钟者，不惧生，不惧死，不惧分离，世间万物，唯情不死，即为长生',
          '你行过黄泉，得见八百里红花，株株情根深种，只是，那朵曼殊沙华，是何时开放的呢',
          '长江大河潮来潮去又是什么样子',
          '我是你头顶的云，是你耳畔的风',
          '一滴生泪，二钱老泪，三分苦泪，四杯悔泪，五寸相思泪，六盅病中泪，七尺别离泪，八味孟婆伤心泪。',
          '三七喜欢长生从黄泉到冥界所有人都知道',
          '长生喜欢三七八百里曼殊沙华亦无一不晓',
          '长生长生不做孟婆之后我去了人间',
          '人间的云是什么样子',
          '人间的风是什么样子',
          '终有一日，你行过黄泉，得见八百里红花，株株情根深种',
          '让我们穿越最深的地狱，然后直抵天堂！',
          '假如你选择的是地狱的尽头，请让我与你一起堕落！',
          '与其在天堂为仆，不如在地狱为主。',
          '若是你的右眼叫你跌倒，就剜出来丢掉。',
          '宁可失去百体中的一体，不叫全身丢在地狱里。',
          '地狱里空空荡荡，魔鬼都在人间。',
          '天堂未必在前方，但地狱一定在身后。',
          '你脚踩的地狱只是天堂的倒影,我唇角的故事也是时间的灰烬。',
          '心恶则到处是地狱，心善则到处是天堂。',
          '但从此八百里黄泉再无孟婆',
          '终有一日你行过黄泉，得见八百里红花，株株情根深种。'
        ]
        let index = Math.floor((Math.random() * welcomeArr.length))
        return `${time}，${this.user.username}，${welcomeArr[index]}`
      }
    },
    mounted () {
      this.welcomeMessage = this.welcome()
      this.$get(`index/${this.user.username}`).then((r) => {
        let data = r.data.data
        this.todayIp = data.todayIp
        this.todayVisitCount = data.todayVisitCount
        this.totalVisitCount = data.totalVisitCount
        let sevenVisitCount = []
        let dateArr = []
        for (let i = 6; i >= 0; i--) {
          let time = moment().subtract(i, 'days').format('MM-DD')
          let contain = false
          for (let o of data.lastSevenVisitCount) {
            if (o.days === time) {
              contain = true
              sevenVisitCount.push(o.count)
            }
          }
          if (!contain) {
            sevenVisitCount.push(0)
          }
          dateArr.push(time)
        }
        let sevenUserVistCount = []
        for (let i = 6; i >= 0; i--) {
          let time = moment().subtract(i, 'days').format('MM-DD')
          let contain = false
          for (let o of data.lastSevenUserVisitCount) {
            if (o.days === time) {
              contain = true
              sevenUserVistCount.push(o.count)
            }
          }
          if (!contain) {
            sevenUserVistCount.push(0)
          }
        }
        this.$refs.count.updateSeries([
          {
            name: '您',
            data: sevenUserVistCount
          },
          {
            name: '总数',
            data: sevenVisitCount
          }
        ], true)
        this.$refs.count.updateOptions({
          xaxis: {
            categories: dateArr
          },
          title: {
            text: '近七日系统访问记录',
            align: 'left'
          }
        }, true, true)
      }).catch((r) => {
        console.error(r)
        this.$message.error('获取首页信息失败')
      })
    }
  }
</script>
<style lang="less">
  .home-page {
    .head-info {
      margin-bottom: .5rem;
      .head-info-card {
        padding: .5rem;
        border-color: #f1f1f1;
        .head-info-avatar {
          display: inline-block;
          float: left;
          margin-right: 1rem;
          img {
            width: 5rem;
            border-radius: 2px;
          }
        }
        .head-info-count {
          display: inline-block;
          float: left;
          .head-info-welcome {
            font-size: 1.05rem;
            margin-bottom: .1rem;
          }
          .head-info-desc {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
            p {
              margin-bottom: 0;
            }
          }
          .head-info-time {
            color: rgba(0, 0, 0, 0.45);
            font-size: .8rem;
            padding: .2rem 0;
          }
        }
      }
    }
    .count-info {
      .visit-count-wrapper {
        padding-left: 0 !important;
        .visit-count {
          padding: .5rem;
          border-color: #f1f1f1;
          .ant-card-body {
            padding: .5rem 1rem !important;
          }
        }
      }
      .project-wrapper {
        padding-right: 0 !important;
        .project-card {
          border: none !important;
          .ant-card-head {
            border-left: 1px solid #f1f1f1 !important;
            border-top: 1px solid #f1f1f1 !important;
            border-right: 1px solid #f1f1f1 !important;
          }
          .ant-card-body {
            padding: 0 !important;
            table {
              width: 100%;
              td {
                width: 50%;
                border: 1px solid #f1f1f1;
                padding: .6rem;
                .project-avatar-wrapper {
                  display:inline-block;
                  float:left;
                  margin-right:.7rem;
                  .project-avatar {
                    color: #42b983;
                    background-color: #d6f8b8;
                  }
                }
              }
            }
          }
          .project-detail {
            display:inline-block;
            float:left;
            text-align:left;
            width: 78%;
            .project-name {
              font-size:.9rem;
              margin-top:-2px;
              font-weight:600;
            }
            .project-desc {
              color:rgba(0, 0, 0, 0.45);
              p {
                margin-bottom:0;
                font-size:.6rem;
                white-space:normal;
              }
            }
          }
        }
      }
    }
  }
</style>
