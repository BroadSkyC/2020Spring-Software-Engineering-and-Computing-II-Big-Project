<template>
    <div class="comment-group">
        <div class="left-comment">
            <a-avatar v-bind:src=comment.imgUrl></a-avatar>
        </div>
        <div class="right-comment">
            <div class="username-rate">
                <div class="username">{{comment.userName}}</div>
<!--                <div class="rate">{{comment.rate}}分</div>-->
                <a-tag  color="green" v-if="comment.creditGrade==='信用极佳'">
                    信用极佳
                </a-tag>
                <a-tag  color="blue" v-if="comment.creditGrade==='信用良好'">
                    信用良好
                </a-tag>
                <a-tag  color="dimgrey" v-if="comment.creditGrade==='信用一般'">
                    信用一般
                </a-tag>
                <div class="rate" >
                    <span class="label">评分:</span>
                    <span class="value">{{ comment.rate }}</span>
                    <a-rate style="font-size: 15px" :value="comment.rate" disabled allowHalf/>
                </div>
            </div>
            <div class="content" v-if="comment.feedback===''">
                该用户未填写评价
            </div>
            <div class="content" v-else>
                {{comment.feedback}}
            </div>
            <div class="end">
               入住于：{{comment.checkInDate}}~{{comment.checkOutDate}}
            </div>
        </div>
    </div>

</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "showComments",
        props: {
            comment: {
                type:Object,
            }
        },
        data() {
            return {
            }
        },
        computed: {
            ...mapGetters([
                'hotelComments',
            ])
        },
        methods: {
            ...mapMutations([
            ]),
            ...mapActions([

            ]),
    }}
</script>

<style scoped lang="less">
    .comment-group {
        display: flex;
        justify-content: flex-start;
        padding-top: 40px;
    }
    .left-comment {
        width: 40px;
        height: 40px;
        margin-right: 0px;
    }
    .left-comment .avatar {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        margin-left: 45px;
    }
    /* 右块 */
    .comment-group .right-comment {
        flex: 1;
    }
    /* 用户评分 */
    .right-comment .username-rate {
        display: flex;
        justify-content: flex-start;
        align-content: center;
    }
    /* 用户名 */
    .username-rate .username {
        font-size: 15px;
        margin-right: 20px;
    }
    .username-rate.rate{
        font-size: 8px;
        color: #666666;
    }
    /* 评论时间 */
    .right-comment .release-time {
        color: #b3b3b3;
        font-size: 30px;
        margin-top: 10px;
    }
    .label{
        color: #999999;
    }
    .value{
        color: #999999;
        margin-right: 10px;
    }
    /* 评论内容 */
    .right-comment .content {
        font-size: 17px;
        color: #353535;
        margin-top: 10px;
    }
    .right-comment .end {
        font-size: 10px;
        color: #353535;
        margin-top: 10px;
    }
</style>