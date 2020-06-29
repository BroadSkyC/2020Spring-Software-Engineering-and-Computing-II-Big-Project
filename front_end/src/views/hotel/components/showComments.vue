<template>
    <div class="comment-group">
        <div class="left-comment">
            <a-avatar v-bind:src=comment.imgUrl></a-avatar>
        </div>
        <div class="right-comment">
            <div class="username-rate">
                <div class="username">{{comment.userName}}{{comment.rate}}</div>
            </div>
            <div class="content">
                {{comment.feedback}}
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
    .comment-group .left-comment {
        width: 70px;
        height: 70px;
        margin-right: 20px;
    }
    .left-comment .avatar {
        width: 70px;
        height: 70px;
        border-radius: 50%;
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
        font-size: 30px;
        margin-right: 20px;
    }
    /* 评论时间 */
    .right-comment .release-time {
        color: #b3b3b3;
        font-size: 30px;
        margin-top: 10px;
    }
    /* 评论内容 */
    .right-comment .content {
        font-size: 30px;
        color: #353535;
        margin-top: 10px;
    }
</style>