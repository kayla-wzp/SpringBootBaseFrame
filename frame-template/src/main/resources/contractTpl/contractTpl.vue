<template>
    <div class="app-container calendar-list-container permit-container">
        <div class="filter-container">
            <el-form :model="searchForm" ref="searchForm" :rules="searchFormRules">
                <el-row type="flex" class="row-bg" justify="space-around">
                    <el-col :span="8">
                        <el-form-item label="模板名称" prop="fileTemplateName">
                            <el-input v-model="searchForm.fileTemplateName"> </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="文件类型" prop="fileType">
                            <el-select
                                v-model="searchForm.fileType"
                                clearable
                                class="search-input"
                                placeholder="请选择"
                            >
                                <el-option
                                    v-for="item in fileTypeOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-button size="small" type="primary" @click="searchParam">
                            <icon name="search"></icon>
                            查询
                        </el-button>
                    </el-col>
                </el-row>
                <el-row :gutter="0">
                    <el-button type="primary" size="small" class="filter-item" icon="plus" @click="addContract">
                        <icon name="plus"></icon>
                        新增
                    </el-button>
                </el-row>
            </el-form>
        </div>
        <el-table
            :data="tableList"
            element-loading-text="加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            border
            fit
            stripe
            highlight-current-row
            tooltip-effect="dark"
        >
            <el-table-column align="center" label="序号" width="60">
                <template slot-scope="scope">
                    <span>{{ (currentPage - 1) * pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="模板编号" min-width="100" prop="fileTemplateId"></el-table-column>
            <el-table-column align="left" label="模板名称" min-width="100" prop="fileTemplateName"> </el-table-column>
            <el-table-column align="left" label="文件类型" min-width="100" prop="fileTypeName"> </el-table-column>
            <el-table-column align="left" label="业务类型" min-width="100" prop="businessTypeName"> </el-table-column>
            <el-table-column align="left" label="最后更新时间" min-width="140" prop="lastUpdateTime"> </el-table-column>
            <el-table-column align="center" label="操作" width="430px">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editContract(scope.row)">
                        <icon name="edit"></icon>
                        修改
                    </el-button>
                    <el-button type="text" size="small" @click="deleteContractTpl(scope.row.fileTemplateId)">
                        <i class="el-icon-delete" style="margin-right: 10%"></i>删除
                    </el-button>
                    <el-button type="text" size="small" @click="openEchoFilling(scope.row.fileTemplateId)"
                        >回显填充配置</el-button
                    >
                    <el-button type="text" size="small" @click="openManualInput(scope.row.fileTemplateId)"
                        >手动输入字段配置</el-button
                    >
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            class="paging"
            @current-change="handleCurrentChange"
            small
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="totalCount"
        >
        </el-pagination>
        <!--新增模板-->
        <add-contract-dialog ref="addContractDialog" @saveForm="queryList"></add-contract-dialog>
        <!--回显填充配置-->
        <echo-filling ref="echoFilling"></echo-filling>
        <!--手动输入字段配置-->
        <manual-input ref="manualInput"></manual-input>
    </div>
</template>

<script>
import AddContractDialog from '@/views/contractTpl/common/addContractDialog'
import echoFilling from '@/views/contractTpl/common/echoFilling'
import manualInput from '@/views/contractTpl/common/manualInput'
export default {
    components: { AddContractDialog, echoFilling, manualInput },
    data() {
        return {
            // 查询验证
            searchFormRules: {
                fileTemplateName: '',
                fileType: ''
            },
            fileTypeOptions: [],
            tableList: [{}],
            searchForm: {
                tplName: '',
                fileType: ''
            },
            pageSize: 20,
            currentPage: 1,
            totalCount: 0
        }
    },
    mounted() {
        // this.queryList()
    },
    methods: {
        // 重置
        clearForm(formName) {
            this.$refs[formName].resetFields()
        },
        // 搜索
        searchParam() {
            this.$refs['searchForm'].validate(valid => {
                if (valid) {
                    this.queryList('1')
                } else {
                    return false
                }
            })
        },
        // 获取列表数据
        queryList(currentPage) {
            if (currentPage) {
                this.currentPage = 1
            }
            const param = {
                ...this.searchForm,
                currentPage: this.currentPage,
                pageSize: this.pageSize
            }
            this.ajax(
                this.apiType().queryDynamicFileTemplateList,
                this.serviceType().query,
                param,
                responseData => {
                    const data = responseData.body
                    this.totalCount = Number(responseData.totalSize)
                    this.tableList = data
                },
                responseData => {
                    this.$alert('请求错误：' + responseData.body.message)
                }
            )
        },
        // 翻页
        handleCurrentChange(currentPage) {
            this.$emit('currentPageChange', currentPage)
            this.currentPage = currentPage
            this.queryList()
        },
        // 新增
        addContract() {
            this.$refs.addContractDialog.openDialog()
        },
        // 修改
        editContract(data) {
            this.$refs.addContractDialog.openDialog(data)
        },
        // 回显填充
        openEchoFilling(id) {
            this.$refs.echoFilling.openDialog(id)
        },
        // 手动输入
        openManualInput(id) {
            this.$refs.manualInput.openDialog(id)
        },
        // 删除
        deleteContractTpl(id) {
            this.$confirm('此操作将删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => {
                    const param = {
                        fileTemplateId: id
                    }
                    this.ajax(this.apiType().deleteDynamicFileTemplate, this.serviceType().api, param, responseData => {
                        this.$message.success('删除成功')
                    })
                })
                .catch(() => {})
        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style permitInput>
.permitInput .row-bg .permitInputClass {
    width: 44%;
}

.el-form-item__label {
    color: #97a8be;
}

.el-input__inner {
    height: 30px;
}

.el-button {
    padding: 7px 12px;
}

.permit-container .el-radio__label {
    padding-left: 4px;
}
</style>
